package session1;

public class JavaCustomer {
    static class Contract {
        Double getBaseFee() {
            return 47.11;
        }
    }

    Contract getContract() {
        return new Contract();
    }
}
