package com.phung.clothshop.domain.dto.ship;

import javax.validation.constraints.NotBlank;

import com.phung.clothshop.domain.entity.ship.EPronvince;
import com.phung.clothshop.utils.customAnnotation.EnumValidCheck;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipReqDTO {

    @NotBlank(message = "ePronvinceFrom can not blank")
    @EnumValidCheck(enumClass = EPronvince.class, message = "Invalid ePronvinceFrom value")
    private String ePronvinceFrom;

    @NotBlank(message = "ePronvinceTo can not blank")
    @EnumValidCheck(enumClass = EPronvince.class, message = "Invalid ePronvinceTo value")
    private String ePronvinceTo;

}
