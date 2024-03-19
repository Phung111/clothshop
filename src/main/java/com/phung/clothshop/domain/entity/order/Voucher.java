package com.phung.clothshop.domain.entity.order;

import lombok.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.springframework.http.HttpStatus;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.utils.DateFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vouchers")
@Where(clause = "deleted = false")
public class Voucher extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(columnDefinition = "bigint default 0")
    private Long percent;

    @Column(columnDefinition = "bigint default 0")
    private Long price;

    public VoucherResDTO toVoucherResDTO() {

        return new VoucherResDTO()
                .setId(id)
                .setDateStart(DateFormat.format(dateStart))
                .setDateEnd(DateFormat.format(dateEnd))
                .setPercent(percent)
                .setPrice(price);
    }

    public void checkDate() {
        Date currentDate = new Date();
        if (currentDate.after(dateEnd)) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Voucher expired");
        }

        if (currentDate.before(dateStart)) {
            String startDate = DateFormat.format(dateStart);
            String startEnd = DateFormat.format(dateEnd);

            throw new CustomErrorException(HttpStatus.BAD_REQUEST, String.format("The voucher is valid from %s to %s",
                    startDate, startEnd));

        }
    }

}
