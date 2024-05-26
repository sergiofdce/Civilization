<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Buildings</title>                
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <div class="header">
                    <a href="index.html" class="arrow"></a>
                    <h1>Buildings</h1>
                </div>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Food Cost</th>
                        <th>Iron Cost</th>
                        <th>Wood Cost</th>
                    </tr>
                    <xsl:for-each select="buildings/building">
                        <tr>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="costs/food_cost"/></td>
                            <td><xsl:value-of select="costs/iron_cost"/></td>
                            <td><xsl:value-of select="costs/wood_cost"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
                <footer class="footer">
                        <p>2024  Civilization. All rights reserved.</p>
                        <p>
                            <a href="https://github.com/sergiofdce/Civilization">GitHub</a> 
                        
                        </p>
                        <p>Authors: Jorge Cortes and Sergio Fernandez</p>
                </footer>
                    
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
