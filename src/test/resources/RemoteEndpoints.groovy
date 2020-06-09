@Grab("groovy-json")
import groovy.json.JsonSlurper

@RestController
class RemoteEndpointApi {

    @GetMapping(value="/banks")
    def getBanks() {
        parseJson(
         """[
            {
              "bankId": {"value": "bankId001"},
              "shortName":"The Royal Bank of Scotland1",
              "fullName":"The Royal Bank of Scotland1",
              "logoUrl":"http://www.red-bank-shoreditch.com/logo.gif",
              "websiteUrl":"http://www.red-bank-shoreditch.com",
              "bankRoutingScheme":"OBP",
              "bankRoutingAddress":"rbs",
              "swiftBic": "swiftBic value",
              "nationalIdentifier": "nationalIdentifier"
            },
             {
              "bankId":{"value": "bankId002"},
              "shortName":"The Royal Bank of Scotland2",
              "fullName":"The Royal Bank of Scotland2",
              "logoUrl":"http://www.red-bank-shoreditch.com/logo.gif",
              "websiteUrl":"http://www.red-bank-shoreditch.com",
              "bankRoutingScheme":"OBP",
              "bankRoutingAddress":"rbs",
              "swiftBic": "swiftBic value",
              "nationalIdentifier": "nationalIdentifier"
            }
            ]
        """
        )
    }

    @GetMapping(value="/banks/{BANK_ID}")
    def getBankById(@PathVariable("BANK_ID") bankId) {
        parseJson(
            """
            {
              "bankId":{"value": "${bankId}"},
              "shortName":"The Royal Bank of Scotland",
              "fullName":"The Royal Bank of Scotland",
              "logoUrl":"http://www.red-bank-shoreditch.com/logo.gif",
              "websiteUrl":"http://www.red-bank-shoreditch.com",
              "bankRoutingScheme":"OBP",
              "bankRoutingAddress":"rbs",
              "swiftBic": "swiftBic value",
              "nationalIdentifier": "nationalIdentifier"
            }
            """
        )
    }
    @GetMapping(value="/banks/{BANK_ID}/accounts")
    def getAllAccountsByBankId(@PathVariable("BANK_ID") bankId) {
        parseJson(
         """{
            "accounts":[
                {
                    "id":"12345678",
                    "label":"NoneLabel",
                    "bank_id":"${bankId}",
                    "number":"12345678"
                },
                 {
                    "id":"87654321",
                    "label":"SecondLabel",
                    "bank_id":"${bankId}",
                    "number":"111"
                }
                ]    
            }
        """
        )
    }

    @GetMapping(value="/banks/{BANK_ID}/{USER_ID}/{ACCOUNT_ID}")
    def getAccountById(@PathVariable("BANK_ID") bankId, @PathVariable("USER_ID") userId, @PathVariable("ACCOUNT_ID") accountId) {
        parseJson(
                """{
    "accountId":{
      "value":"${accountId}"
    },
    "accountType":"AC",
    "balance":"50.89",
    "currency":"EUR",
    "name":"felixsmith",
    "label":"My Account",
    "iban":"DE91 1000 0000 0123 4567 89",
    "number":"546387432",
    "bankId":{
      "value":"${bankId}"
    },
    "lastUpdate":"2020-06-09T12:26:18Z",
    "branchId":"DERBY6",
    "accountRoutingScheme":"IBAN",
    "accountRoutingAddress":"DE91 1000 0000 0123 4567 89",
    "accountRoutings":[{
      "scheme":"IBAN",
      "address":"DE91 1000 0000 0123 4567 89"
    }],
    "accountRules":[],
    "accountHolder":"${userId}"
     }"""
        )
    }

    @GetMapping(value="/banks/{BANK_ID}/{ACCOUNT_ID}")
    def getCoreBankAccounts(@PathVariable("BANK_ID") bankId, @PathVariable("ACCOUNT_ID") accountId) {
        parseJson(
                 """
        {
            "id": "${accountId}--1234511",
            "label": "some label",
            "bankId": "${bankId}",
            "accountType": "mockedAccount",
            "accountRoutings": [
                {
                    "scheme": "OBP",
                    "address": "whatever"
                }
            ]
        }"""
        )
    }
    
    

    def parseJson(jsonStr) {
        new JsonSlurper().parseText(jsonStr)
    }
    
    
}