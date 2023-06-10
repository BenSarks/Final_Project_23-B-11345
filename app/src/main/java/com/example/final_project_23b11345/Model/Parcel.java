package com.example.final_project_23b11345.Model;

import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Parcel implements Parcelable {
    public static final Creator<Parcel> CREATOR = new Creator<Parcel>() {
        @Override
        public Parcel createFromParcel(android.os.Parcel in) {
            return new Parcel(in);
        }

        @Override
        public Parcel[] newArray(int size) {
            return new Parcel[size];
        }
    };
    private String orderDate, receivedDate, deliveryAddress, sendersName, trackingNumber;
    private PackageStatus packageStatus;
    private DeliveryMethod deliveryMethod;
    private boolean supportRequested;
    private FireBaseLatlng location; //used to display the parcel location for deliveries and the picking point location based on the method

    protected Parcel(android.os.Parcel in) {
        trackingNumber = in.readString();
        orderDate = in.readString();
        receivedDate = in.readString();
        deliveryAddress = in.readString();
        sendersName = in.readString();
        supportRequested = in.readByte() != 0;
    }

    public Parcel() {
    }

    public Parcel(String trackingNumber, String orderDate, String receivedDate, String deliveryAddress, String sendersName, PackageStatus packageStatus, DeliveryMethod deliveryMethod) {
        this.trackingNumber = trackingNumber;
        this.orderDate = orderDate;
        this.receivedDate = receivedDate;
        this.deliveryAddress = deliveryAddress;
        this.sendersName = sendersName;
        this.packageStatus = packageStatus;
        this.deliveryMethod = deliveryMethod;
        this.supportRequested = false;
    }

    public FireBaseLatlng getLocation() {
        return location;
    }

    public void setLocation(FireBaseLatlng location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull android.os.Parcel dest, int flags) {
        dest.writeString(trackingNumber);
        dest.writeString(orderDate);
        dest.writeString(receivedDate);
        dest.writeString(deliveryAddress);
        dest.writeString(sendersName);
        dest.writeByte((byte) (supportRequested ? 1 : 0));
    }

    public boolean isSupportRequested() {
        return supportRequested;
    }

    public void setSupportRequested(boolean supportRequested) {
        this.supportRequested = supportRequested;
    }

    public String getTrackingNumber() {
        return trackingNumber + "";
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getSendersName() {
        return sendersName;
    }

    public void setSendersName(String sendersName) {
        this.sendersName = sendersName;
    }

    public PackageStatus getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(PackageStatus packageStatus) {
        this.packageStatus = packageStatus;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "orderDate='" + orderDate + '\'' +
                ", receivedDate='" + receivedDate + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", sendersName='" + sendersName + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", packageStatus=" + packageStatus +
                ", deliveryMethod=" + deliveryMethod +
                ", supportRequested=" + supportRequested +
                '}';
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public enum PackageStatus {
        ARRIVED_AT_FACILITY(0),
        GETTING_READY_FOR_DELIVERY(1),
        DELAYED(2),
        ON_THE_WAY(3),
        READY_TO_PICKUP(4),
        DELIVERED(5),
        COLLECTED(6),
        RETURNED_TO_SENDER(7);

        private final int value;

        PackageStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public int getIndicatorValue() {
            if (value > 4) {
                return 100;
            } else if (value == 4) {
                return 80;
            } else if (value == 3 || value == 2) {
                return 60;
            } else if (value == 1) {
                return 40;
            } else {
                return 20;
            }
        }

        public String getName() {
            return name().replace("_", " ");
        }
    }

    public enum DeliveryMethod {
        EXPRESS_COURIER_SHIPPING(0),
        COLLECT_POINT(1);

        private final int value;

        DeliveryMethod(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name().replace("_", " ");
        }
    }
}
