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
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812QWER1234"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812TYUI5678"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812ASDF4321"]}'

sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210815QWER1234"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210815TYUI5678"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210815ASDF4321"]}'

sleep 3
# query chaincode for channelsales1
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setProduct","Args":["210812GHJK8765","602204 18YLG 9567","Gucci"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setFactory","Args":["210812GHJK8765","abcFactory"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setDelivery","Args":["210812GHJK8765","GucciOutlet"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setStore","Args":["210812GHJK8765","galleria"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setStatusId","Args":["210812GHJK8765","true","bcqr"]}'
sleep 3
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getProduct","Args":["bcqr"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getAllProduct","Args":[""]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSearch","Args":["제품명","583571 1X5CG 6775"]}'

sleep 3
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSerial","Args":["210812QWER1234"]}'
