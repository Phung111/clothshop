package com.phung.clothshop.domain.dto.discount;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountResDTO {

    private Long id;

    private String dateStart;

    private String dateEnd;

    private Long percent;

}
