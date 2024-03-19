package com.phung.clothshop.domain.entity.order;

import javax.persistence.*;

import com.phung.clothshop.domain.BaseEntity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ships")
public class Ship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bigint default 0")
    private Long total;
}
