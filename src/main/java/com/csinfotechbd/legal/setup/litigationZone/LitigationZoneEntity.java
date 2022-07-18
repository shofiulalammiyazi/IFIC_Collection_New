package com.csinfotechbd.legal.setup.litigationZone;

import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.district.DistrictDto;
import com.csinfotechbd.collection.settings.district.DistrictEntity;
import com.csinfotechbd.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*Created By Hasibul Islam
 * 23-Mar-2021 10:30*/

@Getter
@Setter
@Entity
@Table(name = "litigation_zone_entity")
public class LitigationZoneEntity extends CommonEntity {

    private String name;

    /*@ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private LocationEntity location;*/

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Branch> branches = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<DistrictEntity> districts = new ArrayList<>();

    @Transient
    private List<String> branchIds = new ArrayList<>();

    @Transient
    private List<Long> districtIds = new ArrayList<>();

    public void setDistrictIds(List<Long> districtIds) {
        this.districts = districtIds == null || districtIds.isEmpty() ?
                new ArrayList<>() : districtIds.stream().map(DistrictEntity::new).collect(Collectors.toList());
    }

    public LitigationZoneEntity() {
    }

    public LitigationZoneEntity(Tuple data) {
        if(data == null) return;
        Long id = ((Number) data.get("ID")).longValue();
        name = Objects.toString(data.get("NAME"));
        setId(id);
    }


}
