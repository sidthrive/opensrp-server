package org.opensrp.reporting.domain;

import javax.persistence.*;

@Entity
@Table(name="unique_id")
@NamedQueries({
        @NamedQuery(name = UniqueId.FIND_UNIQUE_ID_BY_ANM_IDENTIFIER,
        query = "select r from UniqueId r, ANM a where a.anmIdentifier = :anmIdentifier and r.anm.id = a.id")
})

public class UniqueId {
    public static final String FIND_UNIQUE_ID_BY_ANM_IDENTIFIER = "find.unique.id.by.anm.identifier";
    public static final int INCREMENT = 100;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "anm_id", insertable = true, updatable = true)
    @ManyToOne
    private ANM anm;

    @Column(name = "last_value")
    private Long lastValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ANM getAnm() {
        return anm;
    }

    public void setAnm(ANM anm) {
        this.anm = anm;
    }

    public Long getLastValue() {
        return lastValue;
    }

    public void setLastValue(Long lastValue) {
        this.lastValue = lastValue;
    }
}
