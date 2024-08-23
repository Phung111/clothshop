package com.phung.clothshop.domain.dto.product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.domain.entity.product.ECategory;
import com.phung.clothshop.domain.entity.product.EColor;
import com.phung.clothshop.domain.entity.product.EProductStatus;
import com.phung.clothshop.domain.entity.product.ESize;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.domain.entity.productDetail.ECountry;
import com.phung.clothshop.domain.entity.productDetail.ESeason;
import com.phung.clothshop.domain.entity.productDetail.EShipsFrom;
import com.phung.clothshop.domain.entity.productDetail.EStyle;
import com.phung.clothshop.domain.entity.productDetail.ETopLength;
import com.phung.clothshop.domain.entity.productDetail.ProductDetail;
import com.phung.clothshop.utils.DateFormat;
import com.phung.clothshop.utils.customAnnotation.EnumValidCheck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductReqDTO implements Validator {

    private Long id;

    @NotBlank(message = "name product can not blank")
    private String name;

    @NotBlank(message = "price can not blank")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "price product is not valid number")
    @Pattern(regexp = "^(1000|[1-9]\\d{3,6})$", message = "price must be between 1.000 and 10.000.000")
    private String price;

    @NotBlank(message = "quantity can not blank")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "quantity product is not valid number")
    @Pattern(regexp = "^(?!0|1$)[1-9][0-9]*$", message = "quantity must be at least 1")
    private String quantity;

    private Long sold;

    private Boolean deleted;

    private String decription;

    @NotBlank(message = "category can not blank")
    @EnumValidCheck(enumClass = ECategory.class, message = "Invalid category value")
    private String category;

    @NotBlank(message = "color can not blank")
    @EnumValidCheck(enumClass = EColor.class, message = "Invalid color value")
    private String color;

    @NotBlank(message = "size can not blank")
    @EnumValidCheck(enumClass = ESize.class, message = "Invalid size value")
    private String size;

    // @NotBlank(message = "top length can not blank")
    @EnumValidCheck(enumClass = EProductStatus.class, message = "Invalid top length value")
    private String productStatus;

    @NotBlank(message = "top length can not blank")
    @EnumValidCheck(enumClass = ETopLength.class, message = "Invalid top length value")
    private String topLength;

    @NotBlank(message = "country can not blank")
    @EnumValidCheck(enumClass = ECountry.class, message = "Invalid country value")
    private String country;

    @NotBlank(message = "season can not blank")
    @EnumValidCheck(enumClass = ESeason.class, message = "Invalid season value")
    private String season;

    @NotBlank(message = "style can not blank")
    @EnumValidCheck(enumClass = EStyle.class, message = "Invalid style value")
    private String style;

    @NotBlank(message = "ships from can not blank")
    @EnumValidCheck(enumClass = EShipsFrom.class, message = "Invalid ships from value")
    private String shipsFrom;

    private MultipartFile[] multipartFiles;

    private List<Long> idImageDeletes;

    private String dateStart;

    private String dateEnd;

    private String percent;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductReqDTO dto = (ProductReqDTO) target;

        MultipartFile[] multipartFiles = dto.getMultipartFiles();
        if (multipartFiles != null) { 
            for (int i = 0; i < multipartFiles.length; i++) {
                MultipartFile multipartFile = multipartFiles[i];
                int position = i + 1;

                if (multipartFile != null && !multipartFile.isEmpty()) {
                    // Kiểm tra kích thước file
                    if (multipartFile.getSize() > 10240000) {
                        
                        String message = "File size at index " + position + " must be less than 10 MB";
                        errors.rejectValue("multipartFiles[" + i + "]", "file.size", message);
                        
                    }
    
                    // Kiểm tra loại file
                    String originalFilename = multipartFile.getOriginalFilename();
                    String fileExtension = originalFilename != null
                            ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
                            : null;
                    List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
                    if (fileExtension == null || !allowedExtensions.contains(fileExtension.toLowerCase())) {
                        String message = "Invalid file type at index " + position + ". Allowed types are: jpg, jpeg, png, gif";
                        errors.rejectValue("multipartFiles[" + i + "]", "file.type", message);
                    }
                } 
            }
        }
        
        

        String percent = dto.getPercent();
        if (percent != null && !percent.isEmpty()) {
            if (!percent.matches("^(0|[1-9][0-9]*)$")) {
                errors.rejectValue("percent", "percent.invalidFormat", "percent is not valid number");
            }
            if (!percent.matches("^(100|[1-9]?[0-9])$")) {
                errors.rejectValue("percent", "percent.outOfRange", "percent must be between 0 and 100");
            }
        }

        String dateStart = dto.getDateStart();
        String dateEnd = dto.getDateEnd();
        Date now = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ENGLISH);

        if (dateStart != null && !dateStart.isEmpty() && dateStart != null && !dateStart.isEmpty()) { 
            try {
                Date startDate = formatter.parse(dateStart);
                Date endDate = formatter.parse(dateEnd);
    
                if (endDate.before(now)) {
                    errors.rejectValue("dateEnd", "dateEnd.invalid", "dateEnd must be after today");
                }
    
                if (endDate.before(startDate)) {
                    errors.rejectValue("dateEnd", "dateEnd.beforeStart", "dateEnd must be after dateStart");
                }
            } catch (ParseException e) {
                errors.rejectValue("dateStart", "date.format", "Invalid date format");
                errors.rejectValue("dateEnd", "date.format", "Invalid date format");
            }
            
        }   
    }


    public Product toProduct() {

        ProductDetail productDetail = new ProductDetail();
        productDetail.setTopLength(ETopLength.valueOf(topLength));
        productDetail.setCountry(ECountry.valueOf(country));
        productDetail.setSeason(ESeason.valueOf(season));
        productDetail.setStyle(EStyle.valueOf(style));
        productDetail.setShipsFrom(EShipsFrom.valueOf(shipsFrom));

        Long priceTotalSave = Long.parseLong(price);
        if (percent != null && !percent.isEmpty()) {
            priceTotalSave = priceTotalSave - priceTotalSave*Long.parseLong(percent)/100;
        }

        Long quantityValue = Long.valueOf(quantity);
        Random random = new Random();
        Long soldValue = quantityValue * (60 + random.nextInt(101)) / 100; 

        return new Product()
                .setId(id)
                .setName(name)
                .setPrice(Long.valueOf(price))
                .setPriceTotal(priceTotalSave)
                .setQuantity(Long.valueOf(quantity))
                .setSold(soldValue)
                .setDecription(decription)
                .setCategory(ECategory.valueOf(category))
                .setColor(EColor.valueOf(color))
                .setSize(ESize.valueOf(size))
                .setProductDetail(productDetail);

    }

    // public Discount toDiscount() throws NumberFormatException, ParseException {
    //     return new Discount()
    //             .setDateStart(DateFormat.parse(dateStart))
    //             .setDateEnd(DateFormat.parse(dateEnd))
    //             .setPercent(Long.valueOf(percent));
    // }

    public Discount toDiscount() throws NumberFormatException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

        return new Discount()
                .setDateStart(formatter.parse(dateStart))
                .setDateEnd(formatter.parse(dateEnd))
                .setPercent(Long.valueOf(percent));
    }
}
