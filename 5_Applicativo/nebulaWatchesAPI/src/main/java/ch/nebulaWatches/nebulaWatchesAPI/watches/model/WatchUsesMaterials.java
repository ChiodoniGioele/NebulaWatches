package ch.nebulaWatches.nebulaWatchesAPI.watches.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "watchUsesMaterials")
public class WatchUsesMaterials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "watch_reference", referencedColumnName = "reference")
    private Watch watch;

    @ManyToOne
    @JoinColumn(name = "material_name", referencedColumnName = "name")
    private Material material;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Watch getWatch() {
        return watch;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
