<h1 align="center"> Civilization </h1> <br>
<p align="center">

* Introducción

El juego de Civilization es un juego de estrategia por turnos en el que los jugadores compiten por construir el imperio más poderoso del mundo. El juego se desarrolla a lo largo de varias eras, desde la Edad de Piedra hasta la Era Espacial. Los jugadores pueden construir ciudades, desarrollar tecnologías, entrenar ejércitos y conquistar a sus enemigos.


* Cómo jugar

El juego comienza seleccionando una civilización para jugar. Los jugadores comienzan con una pequeña ciudad y a medida que el juego avanza, los jugadores pueden expandir su ejerctio, mejorar sus edificios, y desarrollar su tecnología.


</p>

<p align="center">
  Civilization game build in Java.
</p>

<p align="center">
  Proximamente en Dispositivos Móviles.
</p>

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
- [UML](#features)
- [Connect DataBase](#feedback)
- [Start Game](#contributors)
- [Programmators](#backers-)
- [Acknowledgments](#acknowledgments)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Introduction

[![Build Status](https://img.shields.io/travis/gitpoint/git-point.svg?style=flat-square)](https://travis-ci.org/gitpoint/git-point)
[![Coveralls](https://img.shields.io/coveralls/github/gitpoint/git-point.svg?style=flat-square)](https://coveralls.io/github/gitpoint/git-point)
[![All Contributors](https://img.shields.io/badge/all_contributors-73-orange.svg?style=flat-square)](./CONTRIBUTORS.md)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg?style=flat-square)](http://commitizen.github.io/cz-cli/)
[![Gitter chat](https://img.shields.io/badge/chat-on_gitter-008080.svg?style=flat-square)](https://gitter.im/git-point)

Juego grafico e interacitivo donde podrás llevar al ejercito de tu Civilización a la conquista y ser el dios del nuevo mundo.

**Only available for PC **

<p align="center">
  <img src = "[http://i.imgur.com/HowF6aM.png](https://cdn.midjourney.com/a6c14838-53a2-4509-9d9f-a4b59b2827bb/0_3.png)" width=350>
</p>

## UML

Representacion del codigo en esquema UML:

  <img src = "[[http://i.imgur.com/HowF6aM.png](https://cdn.midjourney.com/a6c14838-53a2-4509-9d9f-a4b59b2827bb/0_3.png)](https://i.postimg.cc/zBBFTZjZ/Fernandez-Sergio-Cortes-Jorge-UML.png)" width=350>

El esquema UML representa las siguientes clases: 

Civilization: Esta clase representa una civilización en el juego. 
MilitaryUnit: Esta clase representa una unidad militar en el juego. Las unidades militares pueden ser de diferentes tipos, como espadachines, lanceros, cañones, etc.
AttackUnit: Esta clase es una subclase de la clase MilitaryUnit que representa una unidad militar de ataque.
DefenseUnit: Esta clase es una subclase de la clase MilitaryUnit que representa una unidad militar de defensa.
SpecialUnit: Esta clase es una subclase de la clase MilitaryUnit que representa una unidad militar especial.
Building: Esta clase representa un edificio en el juego. Los edificios pueden ser de diferentes tipos, como granjas, herrerías, etc.
Resource: Esta clase representa un recurso en el juego. Los recursos pueden ser de diferentes tipos, como comida, madera, hierro y maná.


## Connect DataBase

Para poder hacer uso del guardado de partida, hace falta configurar la base de datos de PL/SQL.
Para ello necesitaremos crear un nuevo usuario y contraseña para que el juego pueda acceder a las tablas:

alter session set "_ORACLE_SCRIPT"=true;
create user CIVI identified by 123;
GRANT RESOURCE TO CIVI;
grant create session to CIVI;
grant unlimited tablespace to CIVI;
grant dba to CIVI;
grant create view to CIVI;


Después ejecutaremos el script de creación de tablas que se encuentra en M2/BasededatosCivilization.sql


## Start Game

Para iniciar el juego importaremos el proyecto de Eclipse que se encuentra en M3/Civilization_Game



## Contributors

This project follows the [all-contributors](https://github.com/kentcdodds/all-contributors) specification and is brought to you by these [awesome contributors](./CONTRIBUTORS.md).

## Build Process

- Follow the [React Native Guide](https://facebook.github.io/react-native/docs/getting-started.html) for getting started building a project with native code. **A Mac is required if you wish to develop for iOS.**
- Clone or download the repo
- `yarn` to install dependencies
- `yarn run link` to link react-native dependencies
- `yarn start:ios` to start the packager and run the app in the iOS simulator (`yarn start:ios:logger` will boot the application with [redux-logger](<https://github.com/evgenyrodionov/redux-logger>))
- `yarn start:android` to start the packager and run the app in the the Android device/emulator (`yarn start:android:logger` will boot the application with [redux-logger](https://github.com/evgenyrodionov/redux-logger))

Please take a look at the [contributing guidelines](./CONTRIBUTING.md) for a detailed process on how to build your application as well as troubleshooting information.

**Development Keys**: The `CLIENT_ID` and `CLIENT_SECRET` in `api/index.js` are for development purposes and do not represent the actual application keys. Feel free to use them or use a new set of keys by creating an [OAuth application](https://github.com/settings/applications/new) of your own. Set the "Authorization callback URL" to `gitpoint://welcome`.

## Backers [![Backers on Open Collective](https://opencollective.com/git-point/backers/badge.svg)](#backers)

Thank you to all our backers! 🙏 [[Become a backer](https://opencollective.com/git-point#backer)]

<a href="https://opencollective.com/git-point#backers" target="_blank"><img src="https://opencollective.com/git-point/backers.svg?width=890"></a>

## Sponsors [![Sponsors on Open Collective](https://opencollective.com/git-point/sponsors/badge.svg)](#sponsors)

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/git-point#sponsor)]

<a href="https://opencollective.com/git-point/sponsor/0/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/0/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/1/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/1/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/2/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/2/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/3/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/3/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/4/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/4/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/5/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/5/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/6/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/6/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/7/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/7/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/8/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/8/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/9/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/9/avatar.svg"></a>

## Acknowledgments

Thanks to [JetBrains](https://www.jetbrains.com) for supporting us with a [free Open Source License](https://www.jetbrains.com/buy/opensource).
