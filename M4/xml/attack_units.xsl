<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Attack Units</title>
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <div class="header">
                    <a href="index.html" class="arrow"></a>
                    <h1>Attack Units</h1>
                </div>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Base Damage</th>
                        <th>Armour</th>
                        <th>Waste Chance</th>
                        <th>Attack Again Chance</th>
                        <th>Armour Technology</th>
                        <th>Attack Technology</th>
                        <th>Armour Experience</th>
                        <th>Attack Experience</th>
                        <th>Armour Sanctified</th>
                        <th>Attack Sanctified</th>
                        <th>Food Cost</th>
                        <th>Wood Cost</th>
                        <th>Iron Cost</th>
                        <th>Mana Cost</th>
                    </tr>
                    <xsl:for-each select="attack_units/unit">
                        <tr>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="base_damage"/></td>
                            <td><xsl:value-of select="armour"/></td>
                            <td><xsl:value-of select="waste_chance"/></td>
                            <td><xsl:value-of select="attack_again_chance"/></td>
                            <td><xsl:value-of select="plus_stats/armour_technology"/></td>
                            <td><xsl:value-of select="plus_stats/attack_technology"/></td>
                            <td><xsl:value-of select="plus_stats/armour_experience"/></td>
                            <td><xsl:value-of select="plus_stats/attack_experience"/></td>
                            <td><xsl:value-of select="plus_stats/armour_sanctified"/></td>
                            <td><xsl:value-of select="plus_stats/attack_sanctified"/></td>
                            <td><xsl:value-of select="costs/food_cost"/></td>
                            <td><xsl:value-of select="costs/wood_cost"/></td>
                            <td><xsl:value-of select="costs/iron_cost"/></td>
                            <td><xsl:value-of select="costs/mana_cost"/></td>
                        </tr>
                    </xsl:for-each>
                </table>


                 <footer class="footer">
                        <p> 2024  Civilization. All rights reserved.</p>
                        <p>
                            <a href="https://github.com/sergiofdce/Civilization">GitHub</a> 
                        
                        </p>
                        <p>Authors: Jorge Cortes and Sergio Fernandez</p>
                </footer>




            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
