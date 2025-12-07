<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns="http://www.w3.org/1999/xhtml">
    
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    
    <xsl:template match="/">
        <html lang="ru">
        <head>
            <meta charset="UTF-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <title>Магазин косметики - REST API</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 20px;
                    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                    min-height: 100vh;
                }
                .container {
                    max-width: 1200px;
                    margin: 0 auto;
                    background: white;
                    padding: 30px;
                    border-radius: 15px;
                    box-shadow: 0 10px 30px rgba(0,0,0,0.1);
                }
                h1 {
                    color: #e91e63;
                    text-align: center;
                    margin-bottom: 30px;
                    font-size: 2.5em;
                    text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
                }
                .nav-bar {
                    background: linear-gradient(90deg, #e91e63, #9c27b0);
                    padding: 15px;
                    border-radius: 10px;
                    margin-bottom: 30px;
                    display: flex;
                    flex-wrap: wrap;
                    gap: 15px;
                    justify-content: center;
                }
                .nav-bar a {
                    color: white;
                    text-decoration: none;
                    padding: 10px 20px;
                    border-radius: 25px;
                    transition: all 0.3s;
                    font-weight: bold;
                    background: rgba(255,255,255,0.2);
                }
                .nav-bar a:hover {
                    background: rgba(255,255,255,0.3);
                    transform: translateY(-2px);
                }
                .api-section {
                    margin: 30px 0;
                    padding: 20px;
                    border: 1px solid #e0e0e0;
                    border-radius: 10px;
                    background: #f9f9f9;
                }
                .api-section h2 {
                    color: #9c27b0;
                    border-bottom: 2px solid #e91e63;
                    padding-bottom: 10px;
                }
                .endpoint {
                    background: white;
                    padding: 15px;
                    margin: 10px 0;
                    border-radius: 8px;
                    border-left: 4px solid #4caf50;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                }
                .method {
                    display: inline-block;
                    padding: 5px 15px;
                    border-radius: 4px;
                    color: white;
                    font-weight: bold;
                    margin-right: 10px;
                }
                .get { background: #4caf50; }
                .post { background: #2196f3; }
                .put { background: #ff9800; }
                .delete { background: #f44336; }
                .url {
                    font-family: 'Courier New', monospace;
                    background: #f5f5f5;
                    padding: 8px 15px;
                    border-radius: 4px;
                    margin: 10px 0;
                    display: block;
                }
                .description {
                    color: #666;
                    margin-top: 10px;
                }
                .data-display {
                    background: #fff3e0;
                    padding: 20px;
                    border-radius: 10px;
                    margin-top: 20px;
                    border: 1px solid #ffcc80;
                }
                .data-item {
                    padding: 10px;
                    margin: 5px 0;
                    background: white;
                    border-radius: 5px;
                    border-left: 3px solid #ff9800;
                }
                .form-group {
                    margin: 15px 0;
                }
                .form-group label {
                    display: block;
                    margin-bottom: 5px;
                    color: #333;
                    font-weight: bold;
                }
                .form-group input, 
                .form-group textarea,
                .form-group select {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                    font-size: 14px;
                }
                .btn {
                    padding: 12px 25px;
                    border: none;
                    border-radius: 5px;
                    cursor: pointer;
                    font-size: 16px;
                    font-weight: bold;
                    transition: all 0.3s;
                    margin: 5px;
                }
                .btn-primary {
                    background: #e91e63;
                    color: white;
                }
                .btn-primary:hover {
                    background: #c2185b;
                    transform: translateY(-2px);
                }
                .btn-success {
                    background: #4caf50;
                    color: white;
                }
                .btn-success:hover {
                    background: #388e3c;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>✨ Магазин косметики - REST API ✨</h1>
                
                <div class="nav-bar">
                    <a href="/api/products?format=xml">Все товары (XML)</a>
                    <a href="/api/products?format=json">Все товары (JSON)</a>
                    <a href="/api/brands?format=xml">Бренды (XML)</a>
                    <a href="/api/categories?format=xml">Категории (XML)</a>
                    <a href="/">Веб-интерфейс</a>
                    <a href="http://localhost:8080/h2-console" target="_blank">База данных</a>
                </div>
                
                <div class="api-section">
                    <h2>📦 REST API Эндпоинты</h2>
                    
                    <div class="endpoint">
                        <span class="method get">GET</span>
                        <span class="url">/api/products</span>
                        <div class="description">Получить список всех товаров (поддерживает JSON и XML)</div>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method get">GET</span>
                        <span class="url">/api/products/{id}</span>
                        <div class="description">Получить товар по ID</div>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method post">POST</span>
                        <span class="url">/api/products</span>
                        <div class="description">Создать новый товар</div>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method put">PUT</span>
                        <span class="url">/api/products/{id}</span>
                        <div class="description">Обновить товар</div>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method delete">DELETE</span>
                        <span class="url">/api/products/{id}</span>
                        <div class="description">Удалить товар</div>
                    </div>
                </div>
                
                <xsl:if test="products">
                    <div class="data-display">
                        <h2>🛍️ Товары в магазине</h2>
                        <xsl:apply-templates select="products/product"/>
                        
                        <div style="margin-top: 30px;">
                            <h3>Добавить новый товар</h3>
                            <form id="addProductForm">
                                <div class="form-group">
                                    <label>Название:</label>
                                    <input type="text" name="name" required="true"/>
                                </div>
                                <div class="form-group">
                                    <label>Описание:</label>
                                    <textarea name="description"></textarea>
                                </div>
                                <div class="form-group">
                                    <label>Цена:</label>
                                    <input type="number" name="price" step="0.01" required="true"/>
                                </div>
                                <div class="form-group">
                                    <label>Количество:</label>
                                    <input type="number" name="quantity" required="true"/>
                                </div>
                                <button type="button" onclick="addProduct()" class="btn btn-primary">Добавить товар</button>
                            </form>
                        </div>
                    </div>
                </xsl:if>
                
                <xsl:if test="brands">
                    <div class="data-display">
                        <h2>🏷️ Бренды</h2>
                        <xsl:apply-templates select="brands/brand"/>
                    </div>
                </xsl:if>
                
                <xsl:if test="categories">
                    <div class="data-display">
                        <h2>📁 Категории</h2>
                        <xsl:apply-templates select="categories/category"/>
                    </div>
                </xsl:if>
                
                <script>
                    function addProduct() {
                        const form = document.getElementById('addProductForm');
                        const product = {
                            name: form.name.value,
                            description: form.description.value,
                            price: parseFloat(form.price.value),
                            quantity: parseInt(form.quantity.value)
                        };
                        
                        fetch('/api/products', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                                'Accept': 'application/xml'
                            },
                            body: JSON.stringify(product)
                        })
                        .then(response => {
                            if (response.ok) {
                                alert('Товар успешно добавлен!');
                                location.reload();
                            } else {
                                alert('Ошибка при добавлении товара');
                            }
                        })
                        .catch(error => {
                            alert('Ошибка сети: ' + error);
                        });
                    }
                    
                    function deleteProduct(id) {
                        if (confirm('Удалить этот товар?')) {
                            fetch(`/api/products/${id}`, {
                                method: 'DELETE'
                            })
                            .then(response => {
                                if (response.ok) {
                                    alert('Товар удален!');
                                    location.reload();
                                }
                            });
                        }
                    }
                </script>
            </div>
        </body>
        </html>
    </xsl:template>
    
    <xsl:template match="product">
        <div class="data-item">
            <strong><xsl:value-of select="name"/></strong><br/>
            <small>Цена: <xsl:value-of select="price"/> ₽ | Количество: <xsl:value-of select="quantity"/></small><br/>
            <xsl:if test="description">
                <small>Описание: <xsl:value-of select="description"/></small><br/>
            </xsl:if>
            <xsl:if test="brand">
                <small>Бренд: <xsl:value-of select="brand/name"/></small>
            </xsl:if>
            <button onclick="deleteProduct({id})" class="btn" style="background: #f44336; color: white; padding: 5px 10px; font-size: 12px; margin-top: 5px;">
                Удалить
            </button>
        </div>
    </xsl:template>
    
    <xsl:template match="brand">
        <div class="data-item">
            <strong><xsl:value-of select="name"/></strong>
            <xsl:if test="country">
                <br/><small>Страна: <xsl:value-of select="country"/></small>
            </xsl:if>
        </div>
    </xsl:template>
    
    <xsl:template match="category">
        <div class="data-item">
            <strong><xsl:value-of select="name"/></strong>
        </div>
    </xsl:template>
</xsl:stylesheet>