package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.dto.ProductImportDTO;
import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.entity.ProductImportHistory;
import com.piseth.example.spring.phone_shop.exception.ApiException;
import com.piseth.example.spring.phone_shop.exception.ResourceNotFoundException;
import com.piseth.example.spring.phone_shop.mapper.ProductMapper;
import com.piseth.example.spring.phone_shop.repository.ProductImportHistoryRepository;
import com.piseth.example.spring.phone_shop.repository.ProductRepository;
import com.piseth.example.spring.phone_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImportHistoryRepository importHistoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Product create(Product product) {
        String name = "%s %s"
                .formatted(product.getModel().getName(), product.getColor().getName());
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    @Override
    public Product getByModelIdAndColorId(Long modelId, Long colorId) {
        String text = "Product with model id = %s and color id = %d not found.";
        return productRepository.findByModelIdAndColorId(modelId, colorId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, text.formatted(modelId, colorId)));
    }

    @Override
    public void importProduct(ProductImportDTO importDTO) {
        // update available product unit
        Product product = getById(importDTO.getProductId());
        Integer availableUnit = 0;
        if (product.getAvailableUnit() != null) {
            availableUnit = product.getAvailableUnit();
        }
        product.setAvailableUnit(availableUnit + importDTO.getImportUnit());
        productRepository.save(product);

        // save product import history
        ProductImportHistory importHistory = productMapper.productImportHistory(importDTO, product);
        importHistoryRepository.save(importHistory);
    }

    @Override
    public void setSalePrice(Long productId, BigDecimal price) {
        // Business logic: price cannot be 0
        if (price.compareTo(BigDecimal.ZERO) == 0) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Price cannot be zero. Please set a valid sale price.");
        }

        // Continue with normal processing
        Product product = getById(productId);
        product.setSalePrice(price);
        productRepository.save(product);
    }

    @Override
    public void validateStock(Long productId, Integer numberOfUnit) {

    }

    @Override
    public Map<Integer, String> uploadProduct(MultipartFile file) throws IOException {
        Map<Integer, String> map = new HashMap<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheet("products");
        Iterator<Row> iterator = sheet.iterator();
        iterator.next(); // @TODO improve checking error
        while (iterator.hasNext()) {
            int rowNumber = 0;
            try {
                Row row = iterator.next();
                int cellIndex = 0;
                Cell cellNo = row.getCell(cellIndex++);
                rowNumber = (int) cellNo.getNumericCellValue();
                Cell cellModelId = row.getCell(cellIndex++);
                Long modelId = getLongValueFromCell(cellModelId);
                Cell cellColorId = row.getCell(cellIndex++);
                Long colorId = getLongValueFromCell(cellColorId);
                Cell cellImportPrice = row.getCell(cellIndex++);
                Double importPrice = getDoubleValueFromCell(cellImportPrice);
                Cell cellImportUnit = row.getCell(cellIndex++);
                Integer importUnit = getIntValueFromCell(cellImportUnit);
                if (importUnit < 1) {
                    throw new ApiException(HttpStatus.BAD_REQUEST, "Unit must be greater than 0.");
                }
                Cell cellImportDate = row.getCell(cellIndex++);
                LocalDateTime importDate = getOffsetDateTimeFromCell(cellImportDate).toLocalDateTime();
//            Optional<Product> product = productRepository.findByModelIdAndColorId(modelId, colorId);
                Product product = getByModelIdAndColorId(modelId, colorId);
//            System.out.println(modelId);
                Integer availableUnit = 0;
                if (product.getAvailableUnit() != null) {
                    availableUnit = product.getAvailableUnit();
                }
                product.setAvailableUnit(availableUnit + importUnit);
                productRepository.save(product);

                // save product import history
                ProductImportHistory productImportHistory = new ProductImportHistory();
                productImportHistory.setDateImport(importDate);
                productImportHistory.setImportUnit(importUnit);
                productImportHistory.setPricePerUnit(BigDecimal.valueOf(importPrice));
                productImportHistory.setProduct(product);
                importHistoryRepository.save(productImportHistory);
            } catch (Exception e) {
                map.put(rowNumber, e.getMessage());
            }
        }
        System.out.println("Finish");
        return map;
    }

    private Long getLongValueFromCell(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Long.parseLong(cell.getStringCellValue());
        }
        throw new IllegalStateException("Invalid cell type for numeric value.");
    }

    private Double getDoubleValueFromCell(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Double.parseDouble(cell.getStringCellValue());
        }
        throw new IllegalStateException("Invalid cell type for numeric value.");
    }

    private Integer getIntValueFromCell(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Integer.parseInt(cell.getStringCellValue());
        }
        throw new IllegalStateException("Invalid cell type for numeric value.");
    }

    private OffsetDateTime getOffsetDateTimeFromCell(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getLocalDateTimeCellValue().atOffset(ZoneOffset.UTC); // Assuming the date in UTC
        } else if (cell.getCellType() == CellType.STRING) {
            return OffsetDateTime.parse(cell.getStringCellValue());
        }
        throw new IllegalStateException("Invalid cell type for date value.");
    }


}
