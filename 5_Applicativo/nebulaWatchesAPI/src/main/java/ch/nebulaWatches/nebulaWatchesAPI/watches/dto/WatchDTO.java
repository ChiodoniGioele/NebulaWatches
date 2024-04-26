package ch.nebulaWatches.nebulaWatchesAPI.watches.dto;

import java.util.Date;
import java.util.List;

public class WatchDTO {
    private String reference;
    private Float retailPrice;
    private String name;
    private String productionTime;
    private String isLimitedTo;
    private Boolean isBackOpen;
    private Float diameterInMm;
    private Float heightInMm;
    private Float waterResistanceM;
    private String description;
    private String nickname;
    private Float lugWidth;
    // Add other fields as needed...

    private List<String> materialsUsedNames;

    private List<Float> prices;
    private List<Date> dates;


    private String brand;
    private String family;
    private String caseMaterial;
    private String bezelMaterial;
    private String glassMaterial;
    private String coatingMaterial;
    private String watchShape;
    private String dialColor;
    private String dialFinish;
    private String watchIndexes;
    private String hands;
    private String movement;

    // Constructors, getters, and setters...

    public WatchDTO() {
        // Default constructor
    }

    public WatchDTO(String reference, Float retailPrice, String name, Integer movementId, String movementName,
                    String productionTime, String isLimitedTo, Boolean isBackOpen, Float diameterInMm,
                    Float heightInMm, Float waterResistanceM, String description, String nickname, Float lugWidth,
                    List<String> materialsUsedNames, String family, String caseMaterial, String bezelMaterial,
                    String glassMaterial, String coatingMaterial, String watchShape, String dialColor,
                    String dialFinish, String watchIndexes, String hands, String brand) {
        this.reference = reference;
        this.retailPrice = retailPrice;
        this.name = name;
        this.productionTime = productionTime;
        this.isLimitedTo = isLimitedTo;
        this.isBackOpen = isBackOpen;
        this.diameterInMm = diameterInMm;
        this.heightInMm = heightInMm;
        this.waterResistanceM = waterResistanceM;
        this.description = description;
        this.nickname = nickname;
        this.lugWidth = lugWidth;
        this.materialsUsedNames = materialsUsedNames;
        this.family = family;
        this.caseMaterial = caseMaterial;
        this.bezelMaterial = bezelMaterial;
        this.glassMaterial = glassMaterial;
        this.coatingMaterial = coatingMaterial;
        this.watchShape = watchShape;
        this.dialColor = dialColor;
        this.dialFinish = dialFinish;
        this.watchIndexes = watchIndexes;
        this.hands = hands;
        this.brand = brand;
    }

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

    public Boolean getIsBackOpen() {
        return isBackOpen;
    }

    public void setIsBackOpen(Boolean backOpen) {
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

    public List<String> getMaterialsUsedNames() {
        return materialsUsedNames;
    }

    public void setMaterialsUsedNames(List<String> materialsUsedNames) {
        this.materialsUsedNames = materialsUsedNames;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getCaseMaterial() {
        return caseMaterial;
    }

    public void setCaseMaterial(String caseMaterial) {
        this.caseMaterial = caseMaterial;
    }

    public String getBezelMaterial() {
        return bezelMaterial;
    }

    public void setBezelMaterial(String bezelMaterial) {
        this.bezelMaterial = bezelMaterial;
    }

    public String getGlassMaterial() {
        return glassMaterial;
    }

    public void setGlassMaterial(String glassMaterial) {
        this.glassMaterial = glassMaterial;
    }

    public String getCoatingMaterial() {
        return coatingMaterial;
    }

    public void setCoatingMaterial(String coatingMaterial) {
        this.coatingMaterial = coatingMaterial;
    }

    public String getWatchShape() {
        return watchShape;
    }

    public void setWatchShape(String watchShape) {
        this.watchShape = watchShape;
    }

    public String getDialColor() {
        return dialColor;
    }

    public void setDialColor(String dialColor) {
        this.dialColor = dialColor;
    }

    public String getDialFinish() {
        return dialFinish;
    }

    public void setDialFinish(String dialFinish) {
        this.dialFinish = dialFinish;
    }

    public String getWatchIndexes() {
        return watchIndexes;
    }

    public void setWatchIndexes(String watchIndexes) {
        this.watchIndexes = watchIndexes;
    }

    public String getHands() {
        return hands;
    }

    public void setHands(String hands) {
        this.hands = hands;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public List<Float> getPrices() {
        return prices;
    }

    public void setPrices(List<Float> prices) {
        this.prices = prices;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
}

