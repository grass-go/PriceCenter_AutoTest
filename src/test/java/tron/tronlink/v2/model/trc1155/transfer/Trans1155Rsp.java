package tron.tronlink.v2.model.trc1155.transfer;

import java.util.List;

public class Trans1155Rsp {
    private ContractInfo contractInfo;
    private List<Token_transfers> token_transfers;
    public void setContractInfo(ContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }
    public ContractInfo getContractInfo() {
        return contractInfo;
    }

    public void setToken_transfers(List<Token_transfers> token_transfers) {
        this.token_transfers = token_transfers;
    }
    public List<Token_transfers> getToken_transfers() {
        return token_transfers;
    }

}
