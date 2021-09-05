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

docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812AAAA1111"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812QWER1234"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812TYUI5678"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812ASDF4321"]}'

docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210815QWER1234"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210815TYUI5678"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210815ASDF4321"]}'

sleep 3
# query chaincode for channelsales1
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setProduct","Args":["210812GHJK8765","AP0250","Chanel"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setFactory","Args":["210812GHJK8765","Afactory"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setDelivery","Args":["210812GHJK8765","Adelivery"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setStore","Args":["210812GHJK8765","Astore"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"setStatusId","Args":["210812GHJK8765","true","user1"]}'
docker exec cli peer chaincode query -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getSupply","Args":["210812GHJK8765"]}'

docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getProduct","Args":["user1"]}'
docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getProduct","Args":["bcqr"]}'

docker exec cli peer chaincode invoke -o orderer.acornpub.com:7050 -C channelsales1 -n product-cc -c '{"function":"getAllProduct","Args":[""]}'
