package com.phung.clothshop.model.dto.product;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.model.product.ECategory;
import com.phung.clothshop.model.product.EColor;
import com.phung.clothshop.model.product.EProductStatus;
import com.phung.clothshop.model.product.ESize;
import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.model.productDetail.ECountry;
import com.phung.clothshop.model.productDetail.ESeason;
import com.phung.clothshop.model.productDetail.EShipsFrom;
import com.phung.clothshop.model.productDetail.EStyle;
import com.phung.clothshop.model.productDetail.ETopLength;
import com.phung.clothshop.model.productDetail.ProductDetail;
import com.phung.clothshop.utils.customAnnotation.EnumValidCheck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateReqDTO implements Validator {

    private Long id;

    // @NotBlank(message = "name product can not blank")
    private String name;

    // @NotBlank(message = "price can not blank")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "price product is not valid number")
    @Pattern(regexp = "^(1000|[1-9]\\d{3,6})$", message = "price must be between 1.000 and 10.000.000")
    private String price;

    // @NotBlank(message = "quantity can not blank")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "quantity product is not valid number")
    @Pattern(regexp = "^(?:[1-9]|[1-9]\\d{1,2}|1000)$", message = "quantity must be between 1 and 1.000")
    private String quantity;

    private Long sold;

    private Boolean deleted;

    private String decription;

    // @NotBlank(message = "category can not blank")
    @EnumValidCheck(enumClass = ECategory.class, message = "Invalid category value")
    private String eCategory;

    // @NotBlank(message = "color can not blank")
    @EnumValidCheck(enumClass = EColor.class, message = "Invalid color value")
    private String eColor;

    // @NotBlank(message = "size can not blank")
    @EnumValidCheck(enumClass = ESize.class, message = "Invalid size value")
    private String eSize;

    // @NotBlank(message = "top length can not blank")
    @EnumValidCheck(enumClass = EProductStatus.class, message = "Invalid top length value")
    private String eProductStatus;

    // @NotBlank(message = "top length can not blank")
    @EnumValidCheck(enumClass = ETopLength.class, message = "Invalid top length value")
    private String eTopLength;

    // @NotBlank(message = "country can not blank")
    @EnumValidCheck(enumClass = ECountry.class, message = "Invalid country value")
    private String eCountry;

    // @NotBlank(message = "season can not blank")
    @EnumValidCheck(enumClass = ESeason.class, message = "Invalid season value")
    private String eSeason;

    // @NotBlank(message = "style can not blank")
    @EnumValidCheck(enumClass = EStyle.class, message = "Invalid style value")
    private String eStyle;

    // @NotBlank(message = "ships from can not blank")
    @EnumValidCheck(enumClass = EShipsFrom.class, message = "Invalid ships from value")
    private String eShipsFrom;

    private MultipartFile[] multipartFiles;

    private List<Long> idImageDeletes;

    @Override
    public boolean supports(Class<?> clazz) {
        throw new UnsupportedOperationException("Unimplemented method 'supports'");
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile[] multipartFiles = (MultipartFile[]) target;

        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile multipartFile = multipartFiles[i];
            if (multipartFile != null && !multipartFile.isEmpty()) {
                // Kiểm tra kích thước file
                if (multipartFile.getSize() > 512000) {
                    String message = "File size at index " + i + " must be less than 500 KB";
                    errors.rejectValue("multipartFiles[" + i + "]", "file.size", message);
                }

                // Kiểm tra loại file
                String originalFilename = multipartFile.getOriginalFilename();
                String fileExtension = originalFilename != null
                        ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
                        : null;
                List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
                if (fileExtension == null || !allowedExtensions.contains(fileExtension.toLowerCase())) {
                    String message = "Invalid file type at index " + i + ". Allowed types are: jpg, jpeg, png, gif";
                    errors.rejectValue("multipartFiles[" + i + "]", "file.type", message);
                }
            }
        }
    }

    public Product toProduct() {

        ProductDetail productDetail = new ProductDetail();
        productDetail.setETopLength(ETopLength.valueOf(eTopLength));
        productDetail.setECountry(ECountry.valueOf(eCountry));
        productDetail.setESeason(ESeason.valueOf(eSeason));
        productDetail.setEStyle(EStyle.valueOf(eStyle));
        productDetail.setEShipsFrom(EShipsFrom.valueOf(eShipsFrom));

        return new Product()
                .setId(id)
                .setName(name)
                .setPrice(Long.valueOf(price))
                .setQuantity(Long.valueOf(quantity))
                .setDecription(decription)
                .setECategory(ECategory.valueOf(eCategory))
                .setEColor(EColor.valueOf(eColor))
                .setESize(ESize.valueOf(eSize))
                .setEProductStatus(EProductStatus.valueOf(eProductStatus))
                .setProductDetail(productDetail);

    }
}
