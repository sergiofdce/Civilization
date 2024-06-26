<h1 align="center"> Civilization </h1> <br>

<p align="center">
  Civilization game build in Java.
</p>


<p align="center">
  <img src="https://github.com/sergiofdce/Civilization/assets/150951059/7f2d801e-bf25-4176-9913-9aa7d5b6937d" width="750">
</p>


<p align="center">

* Introduction

Civilization is a turn-based strategy game where players compete to build the most powerful empire in the world. Players can build cities, develop technologies, train armies, and conquer their enemies.


* How to Play

The game begins by selecting a civilization to play. Players start with a small city, and as the game progresses, they can expand their armies, upgrade their buildings, and develop their technology.

</p>


<p align="center">
Coming Soon to Mobile Devices.</p>


<p align="center">
  <a href="https://itunes.apple.com/us/app/gitpoint/id1251245162?mt=8">
    <img alt="Download on the App Store" title="App Store" src="http://i.imgur.com/0n2zqHD.png" width="140">
  </a>

  <a href="https://play.google.com/store/apps/details?id=com.gitpoint">
    <img alt="Get it on Google Play" title="Google Play" src="http://i.imgur.com/mtGRPuM.png" width="140">
  </a>
</p>

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
## Table of Contents

- [Introduction](#introduction)
- [UML](#uml)
- [Connect DataBase](#database)
- [Star Game](#play)
- [Programmators](#programmators)
- [Acknowledgments](#acknowledgments)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Introduction

A graphic and interactive game where you can lead your civilization's army to conquest and become the god of the new world.


<p align="center">
    **Only available for PC **
</p>


<p align="center">
  <img src="https://i.imgur.com/wtUi2JD.png" width="750">
</p>



## UML

Representation of the code in UML diagram:

<p align="center">
      <img src="https://github.com/sergiofdce/Civilization/assets/150951059/6a269ee5-da7a-4e0f-b5ed-473bd78ea8ee" width="750">
</p>


The UML diagram represents the following classes:

Civilization: This class represents a civilization in the game.

MilitaryUnit: This class represents a military unit in the game. Military units can be of different types, such as swordsmen, spearmen, cannons, etc.

AttackUnit: This class is a subclass of the MilitaryUnit class that represents an attack military unit.

DefenseUnit: This class is a subclass of the MilitaryUnit class that represents a defense military unit.

SpecialUnit: This class is a subclass of the MilitaryUnit class that represents a special military unit.

Building: This class represents a building in the game. Buildings can be of different types, such as farms, blacksmiths, etc.

Resource: This class represents a resource in the game. Resources can be of different types, such as food, wood, iron, and mana.


## DataBase

In order to use the game's save feature, it is necessary to configure the PL/SQL database.
For this purpose, we will need to create a new username and password so that the game can access the tables:

alter session set "_ORACLE_SCRIPT"=true;
</br>
create user CIVI identified by 123;
</br>
GRANT RESOURCE TO CIVI;
</br>
grant create session to CIVI;
</br>
grant unlimited tablespace to CIVI;
</br>
grant dba to CIVI;
</br>
grant create view to CIVI;



Afterwards, we will execute the table creation script located at M2/BasededatosCivilization.sql.


## Play

To start the game, we will import the Eclipse project located at
</br>M3/Civilization_Game. 
</br>
Then, we will execute Main.java found in the "juego" package.



## Programmators

Frontend: Sergio Fernáncez
</br>
Backend: Jorge Cortés



## Acknowledgments

Thanks to [Esteve Terrades]([https://www.jetbrains.com](https://www.iesesteveterradas.cat/)) for making us this project.
