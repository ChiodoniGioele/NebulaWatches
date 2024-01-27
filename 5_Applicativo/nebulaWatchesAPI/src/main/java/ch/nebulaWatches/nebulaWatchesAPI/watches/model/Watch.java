package ch.nebulaWatches.nebulaWatchesAPI.watches.model;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.List;

@Entity
@Table(name = "watch")
public class Watch {
    @Id
    private String reference;
    @Column(nullable = true)
    private float retailPrice;
    private String name;
    @Column(nullable = true)
    private int movementId;
    private String movementName;
    private String productionTime;
    private String isLimitedTo;
    @Column(nullable = true, columnDefinition = "TINYINT(1)")
    private boolean isBackOpen;
    @Column(nullable = true)
    private float diameterInMm;
    @Column(nullable = true)
    private float heightInMm;
    @Column(name = "water_resistance_m", nullable = true)
    private float waterResistanceM;
    @Column(length = 2500)
    private String description;
    private String nickname;
    @Column(nullable = true)
    private float lugWidth;
    @Lob
    private Blob image;


    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    @ManyToOne
    @JoinColumn(name = "case_material_name")
    private Material caseMaterial;

    @ManyToOne
    @JoinColumn(name = "bezel_material_name")
    private Material bezelMaterial;

    @ManyToOne
    @JoinColumn(name = "glass_material_name")
    private Material glassMaterial;

    @ManyToOne
    @JoinColumn(name = "coating_material_name")
    private Material coatingMaterial;

    @ManyToOne
    @JoinColumn(name = "watch_shape_name")
    private WatchShape watchShape;

    @ManyToOne
    @JoinColumn(name = "dial_color_name")
    private DialColor dialColor;

    @ManyToOne
    @JoinColumn(name = "dial_finish_name")
    private DialFinish dialFinish;

    @ManyToOne
    @JoinColumn(name = "watch_indexes_name")
    private WatchIndexes watchIndexes;

    @ManyToOne
    @JoinColumn(name = "hands_name")
    private Hands hands;

    @OneToMany(mappedBy = "watch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchUsesMaterials> materialsUsed;

    // Getters and setters

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMovementId() {
        return movementId;
    }

    public void setMovementId(int movementId) {
        this.movementId = movementId;
    }

    public String getMovementName() {
        return movementName;
    }

    public void setMovementName(String movementName) {
        this.movementName = movementName;
    }

    public String getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(String productionTime) {
        this.productionTime = productionTime;
    }

    public String getIsLimitedTo() {
        return isLimitedTo;
    }

    public void setIsLimitedTo(String isLimitedTo) {
        this.isLimitedTo = isLimitedTo;
    }

    public boolean isBackOpen() {
        return isBackOpen;
    }

    public void setBackOpen(boolean backOpen) {
        isBackOpen = backOpen;
    }

    public float getDiameterInMm() {
        return diameterInMm;
    }

    public void setDiameterInMm(float diameterInMm) {
        this.diameterInMm = diameterInMm;
    }

    public float getHeightInMm() {
        return heightInMm;
    }

    public void setHeightInMm(float heightInMm) {
        this.heightInMm = heightInMm;
    }

    public float getWaterResistanceM() {
        return waterResistanceM;
    }

    public void setWaterResistanceM(float waterResistanceM) {
        this.waterResistanceM = waterResistanceM;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public float getLugWidth() {
        return lugWidth;
    }

    public void setLugWidth(float lugWidth) {
        this.lugWidth = lugWidth;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Material getCaseMaterial() {
        return caseMaterial;
    }

    public void setCaseMaterial(Material caseMaterial) {
        this.caseMaterial = caseMaterial;
    }

    public Material getBezelMaterial() {
        return bezelMaterial;
    }

    public void setBezelMaterial(Material bezelMaterial) {
        this.bezelMaterial = bezelMaterial;
    }

    public Material getGlassMaterial() {
        return glassMaterial;
    }

    public void setGlassMaterial(Material glassMaterial) {
        this.glassMaterial = glassMaterial;
    }

    public Material getCoatingMaterial() {
        return coatingMaterial;
    }

    public void setCoatingMaterial(Material coatingMaterial) {
        this.coatingMaterial = coatingMaterial;
    }

    public WatchShape getWatchShape() {
        return watchShape;
    }

    public void setWatchShape(WatchShape watchShape) {
        this.watchShape = watchShape;
    }

    public DialColor getDialColor() {
        return dialColor;
    }

    public void setDialColor(DialColor dialColor) {
        this.dialColor = dialColor;
    }

    public DialFinish getDialFinish() {
        return dialFinish;
    }

    public void setDialFinish(DialFinish dialFinish) {
        this.dialFinish = dialFinish;
    }

    public WatchIndexes getWatchIndexes() {
        return watchIndexes;
    }

    public void setWatchIndexes(WatchIndexes watchIndexes) {
        this.watchIndexes = watchIndexes;
    }

    public Hands getHands() {
        return hands;
    }

    public void setHands(Hands hands) {
        this.hands = hands;
    }

    public List<WatchUsesMaterials> getMaterialsUsed() {
        return materialsUsed;
    }

    public void setMaterialsUsed(List<WatchUsesMaterials> materialsUsed) {
        this.materialsUsed = materialsUsed;
    }
}
