import java.io.Serializable;
import java.text.MessageFormat;


public class Order implements Serializable {

    private long orderId;
    private String handlebarType;
    private String handlebarMaterial;
    private String handlebarGearshift;
    private String handleMaterial;

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setHandlebarType(String handlebarType) {
        this.handlebarType = handlebarType;
    }

    public void setHandlebarMaterial(String handlebarMaterial) {
        this.handlebarMaterial = handlebarMaterial;
    }

    public void setHandlebarGearshift(String handlebarGearshift) {
        this.handlebarGearshift = handlebarGearshift;
    }

    public void setHandleMaterial(String handleMaterial) {
        this.handleMaterial = handleMaterial;
    }

    public Order(final long orderId, final String handlebarType,
                 final String handlebarMaterial, final String handlebarGearshift,
                 final String handleMaterial) {
        this.orderId = orderId;
        this.handlebarType = handlebarType;
        this.handlebarMaterial = handlebarMaterial;
        this.handlebarGearshift = handlebarGearshift;
        this.handleMaterial = handleMaterial;
    }

    public Order() {

    }

    public long getOrderId() {
        return this.orderId;
    }

    public String getHandlebarType() {
        return this.handlebarType;
    }

    public String getHandlebarMaterial() {
        return this.handlebarMaterial;
    }

    public String getHandlebarGearshift() {
        return this.handlebarGearshift;
    }

    public String getHandleMaterial() {
        return this.handleMaterial;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Order: '{'orderId=''{0}'', handlebarType=''{1}'', handlebarMaterial=''{2}'', handlebarGearshift=''{3}'', handleMaterial=''{4}'''}'",
                orderId, handlebarType, handlebarMaterial, handlebarGearshift, handleMaterial);
    }
}
