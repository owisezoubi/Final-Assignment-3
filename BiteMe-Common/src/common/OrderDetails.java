package common;

import java.io.Serializable;

public class OrderDetails implements Serializable{

    private static final long serialVersionUID = 1L;
        private String orderId;
        private String date;
        private String category;
        private String quantity;
        private String unitPrice;

        public OrderDetails(String orderId, String date, String category, String quantity, String unitPrice) {
            this.orderId = orderId;
            this.date = date;
            this.category = category;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        @Override
        public String toString() {
            return "OrderDetails [orderId=" + orderId + ", date=" + date + ", category=" + category + ", quantity="
                    + quantity + ", unitPrice=" + unitPrice + "]";
        }

    }