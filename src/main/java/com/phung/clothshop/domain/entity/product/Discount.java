package com.phung.clothshop.domain.entity.product;

import lombok.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.discount.DiscountResDTO;
import com.phung.clothshop.utils.DateFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discount")
@Where(clause = "deleted = false")
public class Discount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(columnDefinition = "bigint default 0")
    private Long percent;

    public DiscountResDTO toDiscountResDTO() {
        return new DiscountResDTO()
                .setId(id)
                .setDateStart(DateFormat.format(dateStart))
                .setDateEnd(DateFormat.format(dateEnd))
                .setPercent(percent);
    }

}
