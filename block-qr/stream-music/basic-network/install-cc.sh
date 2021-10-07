#!/bin/bash
set -ev

# install chaincode for channelsales1
docker exec cli peer chaincode install -l golang -n product-cc -v 1.0 -p chaincode/go
sleep 1
# instantiate chaincode for channelsales1
docker exec cli peer chaincode instantiate -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -v 1.0 -c '{"Args":[""]}' -P "OR ('Sales1Org.member')"
sleep 10
# invoke chaincode for channelsales1
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"initSupply","Args":[""]}'

sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812AAAA1111"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["211007NDUW1872"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["211007RKLW5269"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["211007KPWL4218"]}'

sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["211007EISH0576"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["211007QWFC3741"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["211007YWIF3724"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getProduct","Args":["sohye0312"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getAllProduct","Args":[""]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSearch","Args":["제품명","583571 1X5CG 6775"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSerial","Args":["211007NDUW1872"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getFactoryID","Args":["Rolfo"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getDeliveryID","Args":["Brizzi"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getStoreID","Args":["galleria"]}'
