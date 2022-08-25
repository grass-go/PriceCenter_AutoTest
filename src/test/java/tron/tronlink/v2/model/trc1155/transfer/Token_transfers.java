package tron.tronlink.v2.model.trc1155.transfer;

public class Token_transfers {

    private String transaction_id;
    private long block_ts;
    private String from_address;
    private String to_address;
    private long block;
    private String contract_address;
    private String quant;
    private String approval_amount;
    private String event_type;
    private String contract_type;
    private String token_id;
    private boolean confirmed;
    private String contractRet;
    private String finalResult;
    private TokenInfo tokenInfo;
    private boolean fromAddressIsContract;
    private boolean toAddressIsContract;
    private boolean revert;
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
    public String getTransaction_id() {
        return transaction_id;
    }

    public void setBlock_ts(long block_ts) {
        this.block_ts = block_ts;
    }
    public long getBlock_ts() {
        return block_ts;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }
    public String getFrom_address() {
        return from_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }
    public String getTo_address() {
        return to_address;
    }

    public void setBlock(long block) {
        this.block = block;
    }
    public long getBlock() {
        return block;
    }

    public void setContract_address(String contract_address) {
        this.contract_address = contract_address;
    }
    public String getContract_address() {
        return contract_address;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }
    public String getQuant() {
        return quant;
    }

    public void setApproval_amount(String approval_amount) {
        this.approval_amount = approval_amount;
    }
    public String getApproval_amount() {
        return approval_amount;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }
    public String getEvent_type() {
        return event_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }
    public String getContract_type() {
        return contract_type;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
    public String getToken_id() {
        return token_id;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    public boolean getConfirmed() {
        return confirmed;
    }

    public void setContractRet(String contractRet) {
        this.contractRet = contractRet;
    }
    public String getContractRet() {
        return contractRet;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }
    public String getFinalResult() {
        return finalResult;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }
    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setFromAddressIsContract(boolean fromAddressIsContract) {
        this.fromAddressIsContract = fromAddressIsContract;
    }
    public boolean getFromAddressIsContract() {
        return fromAddressIsContract;
    }

    public void setToAddressIsContract(boolean toAddressIsContract) {
        this.toAddressIsContract = toAddressIsContract;
    }
    public boolean getToAddressIsContract() {
        return toAddressIsContract;
    }

    public void setRevert(boolean revert) {
        this.revert = revert;
    }
    public boolean getRevert() {
        return revert;
    }

}
