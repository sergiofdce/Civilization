<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
    <html>
        <head>
            <title>Defense Units</title>
            <link rel="stylesheet" type="text/css" href="style.css"/>
        </head>
        <body>
            <div class="header">
                <a href="index.html" class="arrow"></a>
                <h1>Defense Units</h1>
            </div>
            <table>
                <tr>
                    <th>Attribute</th>
                    <xsl:for-each select="defense_units/unit">
                        <th><xsl:value-of select="name"/></th>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Base Damage</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="base_damage"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Armour</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="armour"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Waste Chance</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="waste_chance"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Attack Again Chance</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="attack_again_chance"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Armour Technology</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="plus_stats/armour_technology"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Attack Technology</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="plus_stats/attack_technology"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Armour Experience</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="plus_stats/armour_experience"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Attack Experience</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="plus_stats/attack_experience"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Armour Sanctified</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="plus_stats/armour_sanctified"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Attack Sanctified</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="plus_stats/attack_sanctified"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Food Cost</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="costs/food_cost"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Wood Cost</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="costs/wood_cost"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Iron Cost</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="costs/iron_cost"/></td>
                    </xsl:for-each>
                </tr>
                <tr>
                    <td>Mana Cost</td>
                    <xsl:for-each select="defense_units/unit">
                        <td><xsl:value-of select="costs/mana_cost"/></td>
                    </xsl:for-each>
                </tr>
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
