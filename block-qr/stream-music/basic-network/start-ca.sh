#!/bin/bash
set -ev

docker-compose -f docker-compose-ca.yaml up -d ca.sales1.acornpub.com

sleep 1
cd ..
cd application/sdk
node enrollAdmin.js
sleep 1
node registUsers.js
sleep 1
node setProduct.js Z1X2C3V4 602204 Gucci
node setSupply.js Z1X2C3V4 Arrived-D none
node setSupply.js Z1X2C3V4 Arrived-S none
node setSupply.js Z1X2C3V4 true user2
sleep 1
node getSupply.js Z1X2C3V4
sleep 1
node getAllProduct.js user2
