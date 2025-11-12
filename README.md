# 🛍️ springboot-mall

一個以 **Spring Boot** 為基礎開發的電商後台 API 專案，  
主要參考 **Hahow Spring 課程**內容，實作完整的 RESTful 架構與後端邏輯。

---

## 🚀 專案介紹
`springboot-mall` 是一個模擬電商平台的後端系統，  
包含產品管理、使用者註冊 / 登入、購物車與訂單流程等功能，  
以 **Spring Boot** 為核心，實踐分層架構與 API 設計原則。

---

## 🛠 技術棧
| 類別 | 技術 |
|------|------|
| 後端框架 | Spring Boot 3.x |
| 資料庫 | MySQL / H2 (測試環境) |
| ORM | Spring JDBC / JPA (視課程進度而定) |
| 構建工具 | Maven |
| API 標準 | RESTful |
| 測試工具 | Postman / MockMvc |

---

## 📂 專案結構
springboot-mall/
├── src/
│ ├── main/
│ │ ├── java/com/example/mall/
│ │ │ ├── controller/ # 控制層 (處理 API 請求)
│ │ │ ├── service/ # 服務層 (商業邏輯)
│ │ │ ├── dao/ # 資料訪問層
│ │ │ ├── model/ # 資料模型 (Entity / DTO)
│ │ │ └── SpringbootMallApplication.java
│ │ └── resources/
│ │ ├── application.yml
│ │ └── schema.sql / data.sql
│ └── test/
│ └── java/com/example/mall/
└── pom.xml

---
主要功能
- 🛍 商品查詢 / 建立 / 修改 / 刪除  
- 👤 使用者註冊、登入  
- 🛒 購物車功能  
- 📦 訂單建立與查詢  
- 🧪 單元測試與整合測試  

---

環境設定

1. 安裝 **Java 17** 以上版本  
2. 安裝 **Maven**  
3. 設定資料庫連線資訊（application.properties）  

測試 API：

預設埠號：http://localhost:8080

🧾 API 範例
取得商品列表
GET /products

json
[
  {
    "productId": 1,
    "productName": "iPhone 15",
    "price": 38900,
    "category": "手機",
    "imageUrl": "https://example.com/iphone15.jpg"
  }
]
建立訂單
POST /orders

json
{
  "buyItemList": [
    { "productId": 9, "quantity": 1 },
    { "productId": 10, "quantity": 2 }
  ]
}

...等

參考來源：
此專案為 Hahow 線上課程 - Spring Boot 後端開發實戰，
目的在於練習後端系統開發、資料庫操作、RESTful API 設計與分層架構。
