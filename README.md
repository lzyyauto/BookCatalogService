# Book Catalog Service

测试用项目.初始化会创建一个book表

## API 

*   `POST /api/books` - 创建一本新书
*   `GET /api/books` - 获取所有书籍
*   `GET /api/books/{id}` - 根据 ID 获取特定书籍
*   `PUT /api/books/{id}` - 更新特定书籍
*   `DELETE /api/books/{id}` - 删除特定书籍


## 设计模式

单例模式
BookRepository

工厂模式
BookFactory

## 遗漏
1. 全局异常捕获全部赋予500并下发实际错误
2. 接口下发粗暴的绑定了ResponseVO
