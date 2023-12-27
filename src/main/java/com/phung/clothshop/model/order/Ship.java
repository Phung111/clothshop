package com.phung.clothshop.model.order;

import javax.persistence.*;

import com.phung.clothshop.model.BaseEntity;

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

    @Column
    private Long total = 50000L;
}
