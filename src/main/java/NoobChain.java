import com.google.gson.GsonBuilder;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class NoobChain {

    public static ArrayList<Block> blockChain = new ArrayList<Block>();
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
    public static int difficulty = 1;
    public static float minimumTransaction = 0.1f;
    public static Wallet walletA;
    public static Wallet walletB;

    public static void main(String[] args){

        /*blockChain.add(new Block("Hola soy el primer bloque", "0"));
        System.out.println("tratando de minar el bloque 1... ");
        blockChain.get(0).mineBlock(difficulty);

        blockChain.add(new Block("Yo soy el segundo bloque", blockChain.get(blockChain.size()-1).hash));
        System.out.println("tratando de minar el bloque 2... ");
        blockChain.get(1).mineBlock(difficulty);

        blockChain.add(new Block("Yo soy el tercer bloque", blockChain.get(blockChain.size()-1).hash));
        System.out.println("tratando de minar el bloque 3... ");
        blockChain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockChain es valido: "+ isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println("\nEl block chain: ");
        System.out.println(blockchainJson);*/

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //Create the new wallets
        walletA = new Wallet();
        walletB = new Wallet();
        //Test public and private keys
        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
        //Create a test transaction from WalletA to walletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);
        //Verify the signature works and verify it from the public key
        System.out.println("Is signature verified");
        System.out.println(transaction.verifiySignature());

    }

    public static Boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;

        for(int i =1; i<blockChain.size();i++){
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Hash actual no corresponde");
                return false;
            }

            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("El hash previo no corresponde");
                return false;
            }
        }
        return true;
    }
}
