package tron.tools.Controller;

import tron.tools.DAO.FProjectTokenInfoDao;

import java.util.List;

public class FProjectTokenInfoController {
    public static void main(String[] args) throws Exception {
        FProjectTokenInfoDao fp = new FProjectTokenInfoDao();
        List<FProjectTokenInfoDao> gs = fp.query();
        for (FProjectTokenInfoDao fProjectTokenInfoDao : gs) {
            System.out.println(fProjectTokenInfoDao.getContractAddress() + "," + fProjectTokenInfoDao.getDepositedUsd());
        }

    }
}
