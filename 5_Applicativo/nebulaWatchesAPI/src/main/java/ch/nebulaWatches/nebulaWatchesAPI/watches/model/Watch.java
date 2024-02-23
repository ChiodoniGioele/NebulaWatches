package ch.nebulaWatches.nebulaWatchesAPI.watches.model;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "watch")
public class Watch {
    @Id
    private String reference;
    private Float retailPrice;
    private String name;
    private Integer movementId;
    private String movementName;
    private String productionTime;
    private String isLimitedTo;
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isBackOpen;
    private Float diameterInMm;
    private Float heightInMm;
    @Column(name = "water_resistance_m")
    private Float waterResistanceM;
    @Column(length = 2500)
    private String description;
    private String nickname;
    private Float lugWidth;
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

    @JsonIgnore
    @OneToMany(mappedBy = "watch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchUsesMaterials> materialsUsed;

    // Getters and setters

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Float getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMovementId() {
        return movementId;
    }

    public void setMovementId(Integer movementId) {
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

    public Boolean getBackOpen() {
        return isBackOpen;
    }

    public void setBackOpen(Boolean backOpen) {
        isBackOpen = backOpen;
    }

    public Float getDiameterInMm() {
        return diameterInMm;
    }

    public void setDiameterInMm(Float diameterInMm) {
        this.diameterInMm = diameterInMm;
    }

    public Float getHeightInMm() {
        return heightInMm;
    }

    public void setHeightInMm(Float heightInMm) {
        this.heightInMm = heightInMm;
    }

    public Float getWaterResistanceM() {
        return waterResistanceM;
    }

    public void setWaterResistanceM(Float waterResistanceM) {
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

    public Float getLugWidth() {
        return lugWidth;
    }

    public void setLugWidth(Float lugWidth) {
        this.lugWidth = lugWidth;
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

    @JsonIgnore
    public Blob getImage() {
        return image;
    }

    @JsonIgnore
    public byte[] getImageBytes() throws SQLException {
        int blobLength = (int) image.length();
        byte[] blobAsBytes = image.getBytes(1, blobLength);
        return blobAsBytes;
    }

    private void setImage(Blob image) {
        this.image = image;
    }

    public WatchDTO toDTO() {
        WatchDTO watchDTO = new WatchDTO();
        watchDTO.setReference(this.reference);
        watchDTO.setRetailPrice(this.retailPrice);
        watchDTO.setName(this.name);
        watchDTO.setMovementId(this.movementId);
        watchDTO.setMovementName(this.movementName);
        watchDTO.setProductionTime(this.productionTime);
        watchDTO.setIsLimitedTo(this.isLimitedTo);
        watchDTO.setIsBackOpen(this.isBackOpen);
        watchDTO.setDiameterInMm(this.diameterInMm);
        watchDTO.setHeightInMm(this.heightInMm);
        watchDTO.setWaterResistanceM(this.waterResistanceM);
        watchDTO.setDescription(this.description);
        watchDTO.setNickname(this.nickname);
        watchDTO.setLugWidth(this.lugWidth);

        if (this.materialsUsed != null) {
            List<String> materialNames = this.materialsUsed.stream()
                    .map(wum -> wum.getMaterial().getName())
                    .collect(Collectors.toList());
            watchDTO.setMaterialsUsedNames(materialNames);
        }

        watchDTO.setFamily(this.family != null ? this.family.getName() : null);
        watchDTO.setCaseMaterial(this.caseMaterial != null ? this.caseMaterial.getName() : null);
        watchDTO.setBezelMaterial(this.bezelMaterial != null ? this.bezelMaterial.getName() : null);
        watchDTO.setGlassMaterial(this.glassMaterial != null ? this.glassMaterial.getName() : null);
        watchDTO.setCoatingMaterial(this.coatingMaterial != null ? this.coatingMaterial.getName() : null);
        watchDTO.setWatchShape(this.watchShape != null ? this.watchShape.getName() : null);
        watchDTO.setDialColor(this.dialColor != null ? this.dialColor.getName() : null);
        watchDTO.setDialFinish(this.dialFinish != null ? this.dialFinish.getName() : null);
        watchDTO.setWatchIndexes(this.watchIndexes != null ? this.watchIndexes.getName() : null);
        watchDTO.setHands(this.hands != null ? this.hands.getName() : null);
        watchDTO.setBrand(family != null ? family.getBrand().getName() : null);

        return watchDTO;
    }


}
