# Insurance Card
Motorbike Insurance Card using NFC technology (Capstone Project Summer 2015)

# Team
- Đinh Quang Trung
- Nguyễn Hữu Phúc
- Phùng Quang Minh Trí
- Nguyễn Chí Kha

# What it looks like

<img src="Document/Images/Card.jpg"/>

<img src="Document/Images/mobile-1.png"/>

<img src="Document/Images/mobile-2.png"/>

<img src="Document/Images/web-1.png"/>

<img src="Document/Images/web-2.png"/>

# System Overview

<img src="Document/Images/SystemOverview.jpg"/>

# Entity Relationship Diagram

<img src="Document/Images/ERD.jpg"/>

# Database Diagram

<img src="Document/Images/Database.jpg"/>

# Technologies
- Java Servlet
- Hibernate
- MySQL
- Android (with NFC)

# Presentation

https://www.youtube.com/watch?v=xnE0tYBCJ1w

# Documentation

## Project Information

- **Project name**: Insurance Card
- **Project Code**: MIC
- **Product Type**: Website & Android Application
- **Start Date**: May 11th, 2015
- **End Date**: August24th,2015

## Current Situation

When participating in traffic, vehicle owners are required to have compulsory insurance (according to Article 6, Decree on compulsory insurance for civil liability of motor vehicle owners, Decree No. 103/2008/ND-CP by Vietnam Government). Therefore, vehicle owners buy insurance from insurance companies or its agents. They pay insurance premium by cash or in online website and receive an insurance certificate with a term of one year, the term can be shorter in some specific situation. When their insurance out of date, they must buy a new insurance, old certificate will be useless. Traffic police will read insurance certificate to check traffic participants.

## Problem Definition

Below are disadvantages of current situation:

- **Forget insurance’s expired date**: Vehicle owners usually keeps their insurance certificate in wallet or somewhere on their vehicle. However, except in cases of necessity, people are not often check their insurance so they could forget its expired date. An expired insurance is not good while it be revealed by traffic officers and could get worse in case of traffic accident.
- **Hard for traffic officers to check and verify insurance**: Traffic officers must read insurance certificate to check and verify vehicle owner’s information. It can be difficult and hinder their work in some cases as at dark or handwriting illegible on insurance certificate.
- **No mechanism to renew old contract**: customers have to handy register new contract when the old one is expired, this is inconvenient for customers.
- **Insurance certificate made of paper**: It could be torn, wet, smudged and especially is counterfeited.
- **Claim/compensation process is ineffective** between customer and insurance company.
- **Difficult to track and manage number of traffic violations and collisions**: In current scenario, insurance companies almost impossible knows vehicle owner’s history to adjust their insurance policy.

According to Vietnam’s laws, motor vehicle owners must have insurance contract with fixed term and fixed fee for each type of vehicle.

## Proposed Solution

Our proposed solution is to build an insurance NFC card system named “MIC system” to resolve the current situations and compatible with current laws, we also design the system to be scalable so we can deploy this system to a multiple insurance services company in future plan.

MIC system includes a web application and two mobile applications with following functions:

### Feature functions

#### Web application: 

- **Register insurance contract**: user can register a new insurance card with on website using online payment. A staff will contact the user to create contract and sends an insurance NFC card to him/her. If users already have a NFC card, they can use the website to renew current contract.
- **Check card information**: user can login into the website and check for their card’s information.
- **Request compensation**: user can fill data into the sample fields and sends compensation request to the company.
- **Make/manage contracts**: staff can make and manage contracts.
- **Resolve compensation**: staff can receive and resolve compensation requests.
- **Notify contract state**: system will sendan email to notify the insured one when their insurance is expired.
- **Notify compensation state**: system will sendan email to info the insured one when their compensation were accepted or rejected.

#### Insurance card printer (mobile app):

- Simulating NFC card printer: staff can print NFC card.

#### Insurance card checker (mobile app):

- Check card: traffic police and Police Department can check specified motor insurance card expired or not.
- Update the punishment of violator: traffic police and Police Department can update the punishment of violator to the card information.

# Complete documentation

See `Document` directory.