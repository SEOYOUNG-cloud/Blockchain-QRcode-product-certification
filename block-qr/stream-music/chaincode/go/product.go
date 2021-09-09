package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"strconv"
	"strings"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

type SmartContract struct {}

type Supply struct {
	Factory string `json:"factory"`
	Delivery   string `json:"delivery"`
	Store string `json:"store"`
	Status bool `json:"status"`
	UserID string `json:"userid"`
	ProductID string `json:"productid"`
}

type Product struct {
	SerialNum    string `json:"serialnum"`
	Name string `json:"name"`
	Brand string `json:"brand"`
}

type All struct {
	Name string `json:"name"`
	Brand string `json:"brand"`
	Factory string `json:"factory"`
	Delivery   string `json:"delivery"`
	Store string `json:"store"`
	Status bool `json:"status"`
	UserID string `json:"userid"`
	ProductID string `json:"productid"`
}

type SupplyKey struct {
	Key string
	Idx int
}

func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) pb.Response {
	function, args := APIstub.GetFunctionAndParameters()
	
	if function == "initSupply" {
		return s.initSupply(APIstub)
	} else if function == "getSupply" {
		return s.getSupply(APIstub, args)
	} else if function == "setProduct" {
		return s.setProduct(APIstub, args)
	} else if function == "getProduct" {
		return s.getProduct(APIstub, args)
	} else if function == "setFactory" {
		return s.setFactory(APIstub, args)
	} else if function == "setDelivery" {
		return s.setDelivery(APIstub, args)
	} else if function == "setStore" {
		return s.setStore(APIstub, args)
	} else if function == "setStatusId" {
		return s.setStatusId(APIstub, args)
	} else if function == "getAllProduct" {
		return s.getAllProduct(APIstub)
	} else if function == "getSerial" {
		return s.getSerial(APIstub, args)
	} else if function == "getSearch" {
		return s.getSearch(APIstub, args)
	}
	fmt.Println("Please check your function : " + function)
	return shim.Error("Unknown function")
}

func (s *SmartContract) initSupply(APIstub shim.ChaincodeStubInterface) pb.Response {
	//product-Gucci
	product1 := Product{SerialNum: "210812QWER1234", Name: "583571 1X5CG 6775", Brand: "Gucci"}
	product2 := Product{SerialNum: "210812TYUI5678", Name: "660195 17QDT 2582", Brand: "Gucci"}
	product3 := Product{SerialNum: "210812ASDF4321", Name: "443496 DRWAR 9022", Brand: "Gucci"}

	product1AsJSONBytes, _ := json.Marshal(product1)
	APIstub.PutState(product1.SerialNum + "pr", product1AsJSONBytes)

	product2AsJSONBytes, _ := json.Marshal(product2)
	APIstub.PutState(product2.SerialNum + "pr", product2AsJSONBytes)

	product3AsJSONBytes, _ := json.Marshal(product3)
	APIstub.PutState(product3.SerialNum + "pr", product3AsJSONBytes)

	//product-Channel
	product4 := Product{SerialNum: "210815QWER1234", Name: "AS2696 B06364 NE798", Brand: "Chanel"}
	product5 := Product{SerialNum: "210815TYUI5678", Name: "AS2756 B06315 NF024", Brand: "Chanel"}
	product6 := Product{SerialNum: "210815ASDF4321", Name: "AS2785 B06505 ND365", Brand: "Chanel"}

	product4AsJSONBytes, _ := json.Marshal(product4)
	APIstub.PutState(product4.SerialNum + "pr", product4AsJSONBytes)

	product5AsJSONBytes, _ := json.Marshal(product5)
	APIstub.PutState(product5.SerialNum + "pr", product5AsJSONBytes)

	product6AsJSONBytes, _ := json.Marshal(product6)
	APIstub.PutState(product6.SerialNum + "pr", product6AsJSONBytes)

	//supply-Gucci
	var supplykey = SupplyKey{}
	json.Unmarshal(generateKey(APIstub, "latestKey"), &supplykey)

	supply1 := Supply{Factory: "abcFactory", Delivery: "GucciOutlet", Store: "galleria", Status: true, UserID: "bcqr", ProductID: "210812QWER1234"}
	supply2 := Supply{Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210812TYUI5678"}
	supply3 := Supply{Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210812ASDF4321"}

	keyidx1 := strconv.Itoa(supplykey.Idx)
	supply1AsJSONBytes, _ := json.Marshal(supply1)
	var keyString = supplykey.Key + keyidx1
	APIstub.PutState(keyString, supply1AsJSONBytes)
	APIstub.PutState(supply1.ProductID + "su", supply1AsJSONBytes)
	supplykey1AsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState(supply1.ProductID+"Key", supplykey1AsBytes)

	keyidx2 := strconv.Itoa(supplykey.Idx + 1)
	supply2AsJSONBytes, _ := json.Marshal(supply2)
	keyString = supplykey.Key + keyidx2
	APIstub.PutState(keyString, supply2AsJSONBytes)
	APIstub.PutState(supply2.ProductID + "su", supply2AsJSONBytes)
	supplykey.Idx = supplykey.Idx + 1
	supplykey2AsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState(supply2.ProductID+"Key", supplykey2AsBytes)

	keyidx3 := strconv.Itoa(supplykey.Idx + 1)
	supply3AsJSONBytes, _ := json.Marshal(supply3)
	keyString = supplykey.Key + keyidx3
	APIstub.PutState(keyString, supply3AsJSONBytes)
	APIstub.PutState(supply3.ProductID + "su", supply3AsJSONBytes)
	supplykey.Idx = supplykey.Idx + 1
	supplykey3AsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState(supply3.ProductID+"Key", supplykey3AsBytes)

	//supply-Channel
	supply4 := Supply{Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210815QWER1234"}
	supply5 := Supply{Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210815TYUI5678"}
	supply6 := Supply{Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210815ASDF4321"}

	keyidx4 := strconv.Itoa(supplykey.Idx + 1)
	supply4AsJSONBytes, _ := json.Marshal(supply4)
	keyString = supplykey.Key + keyidx4
	APIstub.PutState(keyString, supply4AsJSONBytes)
	APIstub.PutState(supply4.ProductID + "su", supply4AsJSONBytes)
	supplykey.Idx = supplykey.Idx + 1
	supplykey4AsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState(supply4.ProductID+"Key", supplykey4AsBytes)

	keyidx5 := strconv.Itoa(supplykey.Idx + 1)
	supply5AsJSONBytes, _ := json.Marshal(supply5)
	keyString = supplykey.Key + keyidx5
	APIstub.PutState(keyString, supply5AsJSONBytes)
	APIstub.PutState(supply5.ProductID + "su", supply5AsJSONBytes)
	supplykey.Idx = supplykey.Idx + 1
	supplykey5AsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState(supply5.ProductID+"Key", supplykey5AsBytes)

	keyidx6 := strconv.Itoa(supplykey.Idx + 1)
	supply6AsJSONBytes, _ := json.Marshal(supply6)
	keyString = supplykey.Key + keyidx6
	APIstub.PutState(keyString, supply6AsJSONBytes)
	APIstub.PutState(supply6.ProductID + "su", supply6AsJSONBytes)
	supplykey.Idx = supplykey.Idx + 1
	supplykey6AsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState(supply6.ProductID+"Key", supplykey6AsBytes)
	APIstub.PutState("latestKey", supplykey6AsBytes)

	//all-Gucci
	var supplykey_all = SupplyKey{}
	json.Unmarshal(generateKey_all(APIstub, "latestKey_all"), &supplykey_all)

	all1 := All{Name: "583571 1X5CG 6775", Brand: "Gucci", Factory: "abcFactory", Delivery: "GucciOutlet", Store: "galleria", Status: true, UserID: "bcqr", ProductID: "210812QWER1234"}
	all2 := All{Name: "660195 17QDT 2582", Brand: "Gucci", Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210812TYUI5678"}
	all3 := All{Name: "443496 DRWAR 9022", Brand: "Gucci", Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210812ASDF4321"}
	
	keyidx1_all := strconv.Itoa(supplykey_all.Idx)
	all1AsJSONBytes, _ := json.Marshal(all1)
	var keyString_all = supplykey_all.Key + keyidx1_all
	APIstub.PutState(keyString_all, all1AsJSONBytes)
	APIstub.PutState(all1.ProductID + "all", all1AsJSONBytes)
	supplykey1_allAsBytes, _ := json.Marshal(supplykey_all)
	APIstub.PutState(all1.ProductID+"Key_all", supplykey1_allAsBytes)
	var buffer bytes.Buffer
	buffer.WriteString("keystring:"+ keyString_all)

	keyidx2_all := strconv.Itoa(supplykey_all.Idx + 1)
	all2AsJSONBytes, _ := json.Marshal(all2)
	keyString_all = supplykey_all.Key + keyidx2_all
	APIstub.PutState(keyString_all, all2AsJSONBytes)
	APIstub.PutState(all2.ProductID + "all", all2AsJSONBytes)
	supplykey_all.Idx = supplykey_all.Idx + 1
	supplykey2_allAsBytes, _ := json.Marshal(supplykey_all)
	APIstub.PutState(all2.ProductID+"Key_all", supplykey2_allAsBytes)
	buffer.WriteString("keystring:"+ keyString_all)

	keyidx3_all := strconv.Itoa(supplykey_all.Idx + 1)
	all3AsJSONBytes, _ := json.Marshal(all3)
	keyString_all = supplykey_all.Key + keyidx3_all
	APIstub.PutState(keyString_all, all3AsJSONBytes)
	APIstub.PutState(all3.ProductID + "all", all3AsJSONBytes)
	supplykey_all.Idx = supplykey_all.Idx + 1
	supplykey3_allAsBytes, _ := json.Marshal(supplykey_all)
	APIstub.PutState(all3.ProductID+"Key_all", supplykey3_allAsBytes)
	buffer.WriteString("keystring:"+ keyString_all)

	//all-Channel
	all4 := All{Name: "AS2696 B06364 NE798", Brand: "Chanel", Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210815QWER1234"}
	all5 := All{Name: "AS2756 B06315 NF024", Brand: "Chanel", Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210815TYUI5678"}
	all6 := All{Name: "AS2785 B06505 ND365", Brand: "Chanel", Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: "210815ASDF4321"}
	
	keyidx4_all := strconv.Itoa(supplykey_all.Idx + 1)
	all4AsJSONBytes, _ := json.Marshal(all4)
	keyString_all = supplykey_all.Key + keyidx4_all
	APIstub.PutState(keyString_all, all4AsJSONBytes)
	APIstub.PutState(all4.ProductID + "all", all4AsJSONBytes)
	supplykey_all.Idx = supplykey_all.Idx + 1
	supplykey4_allAsBytes, _ := json.Marshal(supplykey_all)
	APIstub.PutState(all4.ProductID+"Key_all", supplykey4_allAsBytes)
	buffer.WriteString("keystring:"+ keyString_all)

	keyidx5_all := strconv.Itoa(supplykey_all.Idx + 1)
	all5AsJSONBytes, _ := json.Marshal(all5)
	keyString_all = supplykey_all.Key + keyidx5_all
	APIstub.PutState(keyString_all, all5AsJSONBytes)
	APIstub.PutState(all5.ProductID + "all", all5AsJSONBytes)
	supplykey_all.Idx = supplykey_all.Idx + 1
	supplykey5_allAsBytes, _ := json.Marshal(supplykey_all)
	APIstub.PutState(all5.ProductID+"Key_all", supplykey5_allAsBytes)
	buffer.WriteString("keystring:"+ keyString_all)

	keyidx6_all := strconv.Itoa(supplykey_all.Idx + 1)
	all6AsJSONBytes, _ := json.Marshal(all6)
	keyString_all = supplykey_all.Key + keyidx6_all
	APIstub.PutState(keyString_all, all6AsJSONBytes)
	APIstub.PutState(all6.ProductID + "all", all6AsJSONBytes)
	supplykey_all.Idx = supplykey_all.Idx + 1
	supplykey6_allAsBytes, _ := json.Marshal(supplykey_all)
	APIstub.PutState(all6.ProductID+"Key_all", supplykey6_allAsBytes)
	APIstub.PutState("latestKey_all", supplykey6_allAsBytes)
	buffer.WriteString("keystring:"+ keyString_all)

	return shim.Success(nil)
}

func (s *SmartContract) getSupply(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {

	productAsBytes, err := APIstub.GetState(args[0] + "pr")
	var buffer bytes.Buffer
	if productAsBytes == nil {
		buffer.WriteString("{\"" + args[0] + "\":[{\"Name\":\"False\"}]}")
		return shim.Success(buffer.Bytes())
	}
	
	if err != nil {
		fmt.Println(err.Error())
	}

	product := Product{}
	json.Unmarshal(productAsBytes, &product)

	
	buffer.WriteString("{")
	buffer.WriteString("\"")
	buffer.WriteString(product.SerialNum)
	buffer.WriteString("\":[{")

	buffer.WriteString("\"Name\":")
	buffer.WriteString("\"")
	buffer.WriteString(product.Name)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Brand\":")
	buffer.WriteString("\"")
	buffer.WriteString(product.Brand)
	buffer.WriteString("\"")

	supply := Supply{}
	supplyAsBytes, err := APIstub.GetState(args[0] + "su")
	json.Unmarshal(supplyAsBytes, &supply)

	buffer.WriteString(", \"Factory\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Factory)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Delivery\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Delivery)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Store\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Store)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Status\":")
	buffer.WriteString("\"")
	buffer.WriteString(strconv.FormatBool(supply.Status))
	buffer.WriteString("\"")

	buffer.WriteString(", \"UserID\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.UserID)
	buffer.WriteString("\"")

	buffer.WriteString("}]}")

	return shim.Success(buffer.Bytes())

}

func generateKey(APIstub shim.ChaincodeStubInterface, key string) []byte {

	var isFirst bool = false

	productkeyAsBytes, err := APIstub.GetState(key)
	if err != nil {
		fmt.Println(err.Error())
	}

	supplykey := SupplyKey{}
	json.Unmarshal(productkeyAsBytes, &supplykey)
	if len(supplykey.Key) == 0 || supplykey.Key == "" {
		isFirst = true
		supplykey.Key = "SU"
	}
	if !isFirst {
		supplykey.Idx = supplykey.Idx + 1
	}

	returnValueBytes, _ := json.Marshal(supplykey)

	return returnValueBytes
}

func generateKey_all(APIstub shim.ChaincodeStubInterface, key string) []byte {

	var isFirst bool = false

	productkeyAsBytes, err := APIstub.GetState(key)
	if err != nil {
		fmt.Println(err.Error())
	}

	supplykey := SupplyKey{}
	json.Unmarshal(productkeyAsBytes, &supplykey)
	if len(supplykey.Key) == 0 || supplykey.Key == "" {
		isFirst = true
		supplykey.Key = "ALL"
	}
	if !isFirst {
		supplykey.Idx = supplykey.Idx + 1
	}

	returnValueBytes, _ := json.Marshal(supplykey)

	return returnValueBytes
}

func (s *SmartContract) setProduct(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}
	
	var product = Product{SerialNum: args[0], Name: args[1], Brand: args[2]}
	productAsJSONBytes, _ := json.Marshal(product)
	APIstub.PutState(product.SerialNum + "pr", productAsJSONBytes)

	var supplykey = SupplyKey{}
	json.Unmarshal(generateKey(APIstub, "latestKey"), &supplykey)
	keyidx := strconv.Itoa(supplykey.Idx)

	var supply = Supply{Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: args[0]}

	

	supplyAsJSONBytes, _ := json.Marshal(supply)
	var keyString = supplykey.Key + keyidx
	APIstub.PutState(keyString, supplyAsJSONBytes)
	APIstub.PutState(supply.ProductID + "su", supplyAsJSONBytes)

	var buffer bytes.Buffer
	buffer.WriteString("keystring:"+ keyString)

	supplykeyAsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState("latestKey", supplykeyAsBytes)
	APIstub.PutState(args[0]+"Key", supplykeyAsBytes)

	//all
	var supplykey_all = SupplyKey{}
	json.Unmarshal(generateKey_all(APIstub, "latestKey_all"), &supplykey_all)
	keyidx_all := strconv.Itoa(supplykey_all.Idx)

	var all = All{Name: args[1], Brand: args[2], Factory: "none", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: args[0]}

	allAsJSONBytes, _ := json.Marshal(all)
	var keyString_all = supplykey_all.Key + keyidx_all
	APIstub.PutState(keyString_all, allAsJSONBytes)
	APIstub.PutState(all.ProductID + "all", allAsJSONBytes)
	buffer.WriteString("keystring:"+ keyString_all)

	supplykey_allAsBytes, _ := json.Marshal(supplykey_all)
	APIstub.PutState("latestKey_all", supplykey_allAsBytes)
	APIstub.PutState(args[0]+"Key_all", supplykey_allAsBytes)

	return shim.Success(buffer.Bytes())
	
}

func (s *SmartContract) getProduct(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {
	
	// Find latestKey
	supplykeyAsBytes, _ := APIstub.GetState("latestKey")
	supplykey := SupplyKey{}
	json.Unmarshal(supplykeyAsBytes, &supplykey)
	idxStr := strconv.Itoa(supplykey.Idx + 1)

	var startKey = "SU0"
	var endKey = supplykey.Key + idxStr
	// fmt.Println(startKey)
	// fmt.Println(endKey)

	resultsIter, err := APIstub.GetStateByRange(startKey, endKey)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIter.Close()
	
	var buffer bytes.Buffer
	supply := Supply{}
	product := Product{}

	bArrayMemberAlreadyWritten := false
	for resultsIter.HasNext() {
		queryResponse, err := resultsIter.Next()
		if err != nil {
			return shim.Error(err.Error())
		}

		json.Unmarshal(queryResponse.Value, &supply)
		productAsBytes, err := APIstub.GetState(supply.ProductID + "pr")
		json.Unmarshal(productAsBytes, &product)
		if args[0] != supply.UserID {
			continue

		}
		buffer.WriteString(supply.UserID)
		
		if bArrayMemberAlreadyWritten == false {
			buffer.WriteString("\n{\"")
			buffer.WriteString(supply.UserID)
			buffer.WriteString("\":[")
		}

		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(", ")
		}
		buffer.WriteString("{\"SerialNum\":")
		buffer.WriteString("\"")
		buffer.WriteString(product.SerialNum)
		buffer.WriteString("\"")
		
		buffer.WriteString(", \"Name\":")
		buffer.WriteString("\"")
		buffer.WriteString(product.Name)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Brand\":")
		buffer.WriteString("\"")
		buffer.WriteString(product.Brand)
		buffer.WriteString("\"")
		
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
		
	}
	buffer.WriteString("]}")
	
	if bArrayMemberAlreadyWritten == false {
		buffer.WriteString("no list for " + args[0])
	}

	return shim.Success(buffer.Bytes())
}

func (s *SmartContract) setFactory(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {

	SupplyAsBytes, err := APIstub.GetState(args[0] + "su")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	supply := Supply{}
	json.Unmarshal(SupplyAsBytes, &supply)

	//all
	allAsBytes, err := APIstub.GetState(args[0] + "all")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	all := All{}
	json.Unmarshal(allAsBytes, &all)

	supply.Factory = args[1]
	all.Factory = args[1]

	supplykeyAsBytes, _ := APIstub.GetState(supply.ProductID+"Key")
	supplykey := SupplyKey{}
	json.Unmarshal(supplykeyAsBytes, &supplykey)
	
	updatedSupplyAsBytes, _ := json.Marshal(supply)

	APIstub.PutState(supply.ProductID + "su", updatedSupplyAsBytes)

	keyidx := strconv.Itoa(supplykey.Idx)
	var keyString = supplykey.Key + keyidx
	APIstub.PutState(keyString, updatedSupplyAsBytes)

	//all
	supplykey_allAsBytes, _ := APIstub.GetState(all.ProductID+"Key_all")
	supplykey_all := SupplyKey{}
	json.Unmarshal(supplykey_allAsBytes, &supplykey_all)
	
	updatedAllAsBytes, _ := json.Marshal(all)

	APIstub.PutState(all.ProductID + "all", updatedAllAsBytes)

	keyidx_all := strconv.Itoa(supplykey_all.Idx)
	var keyString_all = supplykey_all.Key + keyidx_all
	APIstub.PutState(keyString_all, updatedAllAsBytes)
	

	var buffer bytes.Buffer
	buffer.WriteString("keystring:"+ keyString_all)
	buffer.WriteString("[")
	buffer.WriteString("{\"Updated Factory\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Factory)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Delivery\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Delivery)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Store\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Store)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Status\":")
	buffer.WriteString("\"")
	buffer.WriteString(strconv.FormatBool(supply.Status))
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated UserID\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.UserID)
	buffer.WriteString("\"")
	buffer.WriteString("}")
	buffer.WriteString("]")

	return shim.Success(buffer.Bytes())
}

func (s *SmartContract) setDelivery(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {

	SupplyAsBytes, err := APIstub.GetState(args[0] + "su")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	supply := Supply{}
	json.Unmarshal(SupplyAsBytes, &supply)

	//all
	allAsBytes, err := APIstub.GetState(args[0] + "all")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	all := All{}
	json.Unmarshal(allAsBytes, &all)

	supply.Delivery = args[1]
	all.Delivery = args[1]

	supplykeyAsBytes, _ := APIstub.GetState(supply.ProductID+"Key")
	supplykey := SupplyKey{}
	json.Unmarshal(supplykeyAsBytes, &supplykey)
	
	updatedSupplyAsBytes, _ := json.Marshal(supply)

	APIstub.PutState(supply.ProductID + "su", updatedSupplyAsBytes)

	keyidx := strconv.Itoa(supplykey.Idx)
	var keyString = supplykey.Key + keyidx
	APIstub.PutState(keyString, updatedSupplyAsBytes)

	//all
	supplykey_allAsBytes, _ := APIstub.GetState(all.ProductID+"Key_all")
	supplykey_all := SupplyKey{}
	json.Unmarshal(supplykey_allAsBytes, &supplykey_all)
	
	updatedAllAsBytes, _ := json.Marshal(all)

	APIstub.PutState(all.ProductID + "all", updatedAllAsBytes)

	keyidx_all := strconv.Itoa(supplykey_all.Idx)
	var keyString_all = supplykey_all.Key + keyidx_all
	APIstub.PutState(keyString_all, updatedAllAsBytes)
	

	var buffer bytes.Buffer
	buffer.WriteString("keystring:"+ keyString_all)
	buffer.WriteString("[")
	buffer.WriteString("{\"Updated Factory\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Factory)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Delivery\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Delivery)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Store\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Store)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Status\":")
	buffer.WriteString("\"")
	buffer.WriteString(strconv.FormatBool(supply.Status))
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated UserID\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.UserID)
	buffer.WriteString("\"")
	buffer.WriteString("}")
	buffer.WriteString("]")

	return shim.Success(buffer.Bytes())
}
func (s *SmartContract) setStore(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {

	SupplyAsBytes, err := APIstub.GetState(args[0] + "su")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	supply := Supply{}
	json.Unmarshal(SupplyAsBytes, &supply)

	//all
	allAsBytes, err := APIstub.GetState(args[0] + "all")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	all := All{}
	json.Unmarshal(allAsBytes, &all)

	supply.Store = args[1]
	all.Store = args[1]

	supplykeyAsBytes, _ := APIstub.GetState(supply.ProductID+"Key")
	supplykey := SupplyKey{}
	json.Unmarshal(supplykeyAsBytes, &supplykey)
	
	updatedSupplyAsBytes, _ := json.Marshal(supply)

	APIstub.PutState(supply.ProductID + "su", updatedSupplyAsBytes)

	keyidx := strconv.Itoa(supplykey.Idx)
	var keyString = supplykey.Key + keyidx
	APIstub.PutState(keyString, updatedSupplyAsBytes)

	//all
	supplykey_allAsBytes, _ := APIstub.GetState(all.ProductID+"Key_all")
	supplykey_all := SupplyKey{}
	json.Unmarshal(supplykey_allAsBytes, &supplykey_all)
	
	updatedAllAsBytes, _ := json.Marshal(all)

	APIstub.PutState(all.ProductID + "all", updatedAllAsBytes)

	keyidx_all := strconv.Itoa(supplykey_all.Idx)
	var keyString_all = supplykey_all.Key + keyidx_all
	APIstub.PutState(keyString_all, updatedAllAsBytes)
	

	var buffer bytes.Buffer
	buffer.WriteString("keystring:"+ keyString_all)
	buffer.WriteString("[")
	buffer.WriteString("{\"Updated Factory\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Factory)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Delivery\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Delivery)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Store\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Store)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Status\":")
	buffer.WriteString("\"")
	buffer.WriteString(strconv.FormatBool(supply.Status))
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated UserID\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.UserID)
	buffer.WriteString("\"")
	buffer.WriteString("}")
	buffer.WriteString("]")

	return shim.Success(buffer.Bytes())
}
func (s *SmartContract) setStatusId(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {
	var b bool

	SupplyAsBytes, err := APIstub.GetState(args[0] + "su")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	supply := Supply{}
	json.Unmarshal(SupplyAsBytes, &supply)

	//all
	allAsBytes, err := APIstub.GetState(args[0] + "all")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	all := All{}
	json.Unmarshal(allAsBytes, &all)

	b, _ = strconv.ParseBool(args[1])
	supply.Status = b
	supply.UserID = args[2]
	all.Status = b
	all.UserID = args[2]


	supplykeyAsBytes, _ := APIstub.GetState(supply.ProductID+"Key")
	supplykey := SupplyKey{}
	json.Unmarshal(supplykeyAsBytes, &supplykey)
	
	updatedSupplyAsBytes, _ := json.Marshal(supply)

	APIstub.PutState(supply.ProductID + "su", updatedSupplyAsBytes)

	keyidx := strconv.Itoa(supplykey.Idx)
	var keyString = supplykey.Key + keyidx
	APIstub.PutState(keyString, updatedSupplyAsBytes)

	//all
	supplykey_allAsBytes, _ := APIstub.GetState(all.ProductID+"Key_all")
	supplykey_all := SupplyKey{}
	json.Unmarshal(supplykey_allAsBytes, &supplykey_all)
	
	updatedAllAsBytes, _ := json.Marshal(all)

	APIstub.PutState(all.ProductID + "all", updatedAllAsBytes)

	keyidx_all := strconv.Itoa(supplykey_all.Idx)
	var keyString_all = supplykey_all.Key + keyidx_all
	APIstub.PutState(keyString_all, updatedAllAsBytes)
	

	var buffer bytes.Buffer
	buffer.WriteString("keystring:"+ keyString_all)
	buffer.WriteString("[")
	buffer.WriteString("{\"Updated Factory\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Factory)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Delivery\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Delivery)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Store\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.Store)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated Status\":")
	buffer.WriteString("\"")
	buffer.WriteString(strconv.FormatBool(supply.Status))
	buffer.WriteString("\"")

	buffer.WriteString(", \"Updated UserID\":")
	buffer.WriteString("\"")
	buffer.WriteString(supply.UserID)
	buffer.WriteString("\"")
	buffer.WriteString("}")
	buffer.WriteString("]")

	return shim.Success(buffer.Bytes())
}
func (s *SmartContract) getAllProduct(APIstub shim.ChaincodeStubInterface) pb.Response {
	
	supplykey_allAsBytes, _ := APIstub.GetState("latestKey_all")
	supplykey_all := SupplyKey{}
	json.Unmarshal(supplykey_allAsBytes, &supplykey_all)
	idxStr := strconv.Itoa(supplykey_all.Idx + 1)

	var startKey = "ALL0"
	var endKey = supplykey_all.Key + idxStr

	resultsIter, err := APIstub.GetStateByRange(startKey, endKey)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIter.Close()
	
	var buffer bytes.Buffer
	buffer.WriteString("[")
	bArrayMemberAlreadyWritten := false
	for resultsIter.HasNext() {
		queryResponse, err := resultsIter.Next()
		if err != nil {
			return shim.Error(err.Error())
		}

		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResponse.Key)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Record\":")
		
		buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")
	return shim.Success(buffer.Bytes())
}

func (s *SmartContract) getSerial(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {

	allAsBytes, _ := APIstub.GetState(args[1] + "all")

	all:= All{}
	json.Unmarshal(allAsBytes, &all)

	if args[0] != strings.ToUpper(all.Brand) {
		return shim.Error("Failed to get product")
	}
	
	var buffer bytes.Buffer
	buffer.WriteString("{")
	buffer.WriteString("\"")
	buffer.WriteString(all.ProductID)
	buffer.WriteString("\":[{")

	buffer.WriteString("\"Name\":")
	buffer.WriteString("\"")
	buffer.WriteString(all.Name)
	buffer.WriteString("\"")

	buffer.WriteString(", \"Brand\":")
	buffer.WriteString("\"")
	buffer.WriteString(all.Brand)
	buffer.WriteString("\"")

	buffer.WriteString(", \"UserID\":")
	buffer.WriteString("\"")
	buffer.WriteString(all.UserID)
	buffer.WriteString("\"")

	buffer.WriteString("}]}")

	return shim.Success(buffer.Bytes())
}

func (s *SmartContract) getSearch(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {
	
	supplykey_allAsBytes, _ := APIstub.GetState("latestKey_all")
	supplykey_all := SupplyKey{}
	json.Unmarshal(supplykey_allAsBytes, &supplykey_all)
	idxStr := strconv.Itoa(supplykey_all.Idx + 1)

	var startKey = "ALL0"
	var endKey = supplykey_all.Key + idxStr

	resultsIter, err := APIstub.GetStateByRange(startKey, endKey)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIter.Close()
	
	var buffer bytes.Buffer
	all := All{}

	buffer.WriteString("[")
	bArrayMemberAlreadyWritten := false
	write_String := false
	for resultsIter.HasNext() {
		queryResponse, err := resultsIter.Next()
		if err != nil {
			return shim.Error(err.Error())
		}

		json.Unmarshal(queryResponse.Value, &all)
		
		switch args[0] {
		case "제품명":
			if args[1] == all.Name {
				write_String = true
			}
		case "브랜드":
			if args[1] == all.Brand {
				write_String = true
			}
		case "제조업체":
			if args[1] == all.Factory {
				write_String = true
			}
		case "유통업체":
			if args[1] == all.Delivery {
				write_String = true
			}
		default:
			if args[1] == all.Store {
				write_String = true
			}
		} 

		if write_String != true {
			continue
		}
		
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResponse.Key)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Record\":")
		
		buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		write_String = false
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")
	return shim.Success(buffer.Bytes())
}

func main() {

	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}
