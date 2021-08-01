package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"strconv"
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
	//Title    string `json:"title"`
	//Singer   string `json:"singer"`
	//Price    string `json:"price"`
	//WalletID    string `json:"walletid"`
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
	} else if function == "getAllProduct" {
		return s.getAllProduct(APIstub, args)
	} else if function == "setSupply" {
		return s.setSupply(APIstub, args)
	}
	fmt.Println("Please check your function : " + function)
	return shim.Error("Unknown function")
}

func (s *SmartContract) initSupply(APIstub shim.ChaincodeStubInterface) pb.Response {
	//product
	product1 := Product{SerialNum: "1Q2W3E4R", Name: "466492", Brand: "Gucci"}
	product2 := Product{SerialNum: "5T6Y7U8I", Name: "456126", Brand: "Gucci"}
	product3 := Product{SerialNum: "A1S2D3F4", Name: "AP0214", Brand: "Chanel"}

	product1AsJSONBytes, _ := json.Marshal(product1)
	APIstub.PutState(product1.SerialNum + "pr", product1AsJSONBytes)

	product2AsJSONBytes, _ := json.Marshal(product2)
	APIstub.PutState(product2.SerialNum + "pr", product2AsJSONBytes)

	product3AsJSONBytes, _ := json.Marshal(product3)
	APIstub.PutState(product3.SerialNum + "pr", product3AsJSONBytes)

	//supply
	var supplykey = SupplyKey{}
	json.Unmarshal(generateKey(APIstub, "latestKey"), &supplykey)

	supply1 := Supply{Factory: "Arrived-F", Delivery: "Arrived-D", Store: "none", Status: false, UserID: "none", ProductID: "1Q2W3E4R"}
	supply2 := Supply{Factory: "Arrived-F", Delivery: "Arrived-D", Store: "Arrived-S", Status: false, UserID: "none", ProductID: "5T6Y7U8I"}
	supply3 := Supply{Factory: "Arrived-F", Delivery: "Arrived-D", Store: "Arrived-S", Status: true, UserID: "user1", ProductID: "A1S2D3F4"}

	keyidx1 := strconv.Itoa(supplykey.Idx)
	supply1AsJSONBytes, _ := json.Marshal(supply1)
	var keyString = supplykey.Key + keyidx1
	APIstub.PutState(keyString, supply1AsJSONBytes)
	APIstub.PutState(supply1.ProductID + "su", supply1AsJSONBytes)

	keyidx2 := strconv.Itoa(supplykey.Idx + 1)
	supply2AsJSONBytes, _ := json.Marshal(supply2)
	keyString = supplykey.Key + keyidx2
	APIstub.PutState(keyString, supply2AsJSONBytes)
	APIstub.PutState(supply2.ProductID + "su", supply2AsJSONBytes)

	keyidx3 := strconv.Itoa(supplykey.Idx + 2)
	supply3AsJSONBytes, _ := json.Marshal(supply3)
	keyString = supplykey.Key + keyidx3
	APIstub.PutState(keyString, supply3AsJSONBytes)
	APIstub.PutState(supply3.ProductID + "su", supply3AsJSONBytes)

	supplykey.Idx = supplykey.Idx + 2
	supplykeyAsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState("latestKey", supplykeyAsBytes)

	return shim.Success(nil)
}

func (s *SmartContract) getSupply(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {

	productAsBytes, err := APIstub.GetState(args[0] + "pr")
	var buffer bytes.Buffer
	if productAsBytes == nil {
		buffer.WriteString(args[0] + ": False")
		return shim.Success(buffer.Bytes())
	}
	
	if err != nil {
		//buffer.WriteString(args[0] + ": False")
		// return shim.Error(args[0] + ": False")
		fmt.Println(err.Error())
	}

	product := Product{}
	json.Unmarshal(productAsBytes, &product)

	
	buffer.WriteString("[")
	// bArrayMemberAlreadyWritten := false

	// if bArrayMemberAlreadyWritten == true {
	// 	buffer.WriteString(",")
	// }
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
	// bArrayMemberAlreadyWritten = true
	buffer.WriteString("]\n")

	supply := Supply{}
	supplyAsBytes, err := APIstub.GetState(args[0] + "su")
	json.Unmarshal(supplyAsBytes, &supply)

	buffer.WriteString("[")
	buffer.WriteString("{\"Factory\":")
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

	buffer.WriteString("}")
	buffer.WriteString("]\n")

	if supply.Store == "none" && supply.Status == false {
		buffer.WriteString(args[0] + ": True, not on sale.")
	} else if supply.Store == "Arrived-S" && supply.Status == false {
		buffer.WriteString(args[0] + ": True, on sale.")
	} else if supply.Store == "Arrived-S" && supply.Status == true {
		buffer.WriteString(args[0] + ": True, already sold. UserID: " + supply.UserID)
	}

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
	// var tempIdx string
	// tempIdx = strconv.Itoa(musickey.Idx)
	// fmt.Println(musickey)
	// fmt.Println("Key is " + strconv.Itoa(len(musickey.Key)))
	if len(supplykey.Key) == 0 || supplykey.Key == "" {
		isFirst = true
		supplykey.Key = "SU"
	}
	if !isFirst {
		supplykey.Idx = supplykey.Idx + 1
	}

	// fmt.Println("Last MusicKey is " + musickey.Key + " : " + tempIdx)
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

	var supply = Supply{Factory: "Arrived-F", Delivery: "none", Store: "none", Status: false, UserID: "none", ProductID: args[0]}

	

	supplyAsJSONBytes, _ := json.Marshal(supply)
	var keyString = supplykey.Key + keyidx
	APIstub.PutState(keyString, supplyAsJSONBytes)
	APIstub.PutState(supply.ProductID + "su", supplyAsJSONBytes)

	var buffer bytes.Buffer
	buffer.WriteString("keystring:"+ keyString)

	supplykeyAsBytes, _ := json.Marshal(supplykey)
	APIstub.PutState("latestKey", supplykeyAsBytes)
	APIstub.PutState(args[0]+"Key", supplykeyAsBytes)

	// return shim.Success(nil)
	return shim.Success(buffer.Bytes())
	
}

func (s *SmartContract) getAllProduct(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {
	
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
	// var isTrue bool = false
	buffer.WriteString("\n[")
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
			// isTrue = true
		}
		buffer.WriteString(supply.UserID)

		// if err != nil {
		// 	fmt.Println(err.Error())
		// }
		
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString(string(queryResponse.Key))
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
		
		//buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
		
	}
	buffer.WriteString("]\n")
	
	if bArrayMemberAlreadyWritten == false {
		buffer.WriteString("no list for " + args[0])
	}

	return shim.Success(buffer.Bytes())
}

func (s *SmartContract) setSupply(APIstub shim.ChaincodeStubInterface, args []string) pb.Response {
	// var tokenFromKey, tokenToKey int // Asset holdings
	// var musicprice int // Transaction value
	// var err error
	var b bool

	// if len(args) != 2 {
	// 	return shim.Error("Incorrect number of arguments. Expecting 2")
	// }

	// musicAsBytes, err := APIstub.GetState(args[1])
	// if err != nil {
	// 	return shim.Error(err.Error())
	// }
	// music := Music{}
	// json.Unmarshal(musicAsBytes, &music)
	// musicprice, _ = strconv.Atoi(music.Price)
	// SellerAsBytes, err := APIstub.GetState(music.WalletID)
	// if err != nil {
	// 	return shim.Error("Failed to get state")
	// }
	// if SellerAsBytes == nil {
	// 	return shim.Error("Entity not found")
	// }
	// seller := Wallet{}
	// json.Unmarshal(SellerAsBytes, &seller)
	// tokenToKey, _ = strconv.Atoi(seller.Token)
	SupplyAsBytes, err := APIstub.GetState(args[0] + "su")
	if err != nil {
		return shim.Error("Failed to get state")
	}
	if SupplyAsBytes == nil {
		return shim.Error("Entity not found")
	}
	supply := Supply{}
	json.Unmarshal(SupplyAsBytes, &supply)
	// tokenFromKey, _ = strconv.Atoi(customer.Token)
	// customer.Token = strconv.Itoa(tokenFromKey - musicprice)
	// seller.Token = strconv.Itoa(tokenToKey + musicprice)

	if args[1] == "Arrived-D" {
		supply.Delivery = args[1]
	} else if args[1] == "Arrived-S" {
		supply.Store = args[1]
	} else if args[1] == "true" {
		b, _ = strconv.ParseBool(args[1])
		supply.Status = b
		supply.UserID = args[2]
	}

	supplykeyAsBytes, _ := APIstub.GetState(supply.ProductID+"Key")
	supplykey := SupplyKey{}
	json.Unmarshal(supplykeyAsBytes, &supplykey)
	// var supplykey = SupplyKey{}
	// json.Unmarshal(generateKey(APIstub, "latestKey"), &supplykey)
	// keyidx := strconv.Itoa(supplykey.Idx)
	// var keyString = supplykey.Key + keyidx
	
	
	updatedSupplyAsBytes, _ := json.Marshal(supply)

	
	//updatedSellerAsBytes, _ := json.Marshal(seller)
	APIstub.PutState(supply.ProductID + "su", updatedSupplyAsBytes)

	keyidx := strconv.Itoa(supplykey.Idx)
	var keyString = supplykey.Key + keyidx
	APIstub.PutState(keyString, updatedSupplyAsBytes)

	// productAsBytes, err := APIstub.GetState(supply.ProductID + "pr") //update product
	// APIstub.PutState(supply.ProductID + "pr", productAsBytes)


	//APIstub.PutState(music.WalletID, updatedSellerAsBytes)
	// buffer is a JSON array containing QueryResults
	var buffer bytes.Buffer
	buffer.WriteString("keystring:"+ keyString)
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
	buffer.WriteString("]\n")

	return shim.Success(buffer.Bytes())
}

func main() {

	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}
