package com.unisoft.collection.samd.setup.temporaryjobdelegation;

public enum TemporaryJobDelegationStatus {

        Waiting("Waiting"),
        Approved("Approved"),
        Reverse("Reverse"),
        Decline("Decline");

        private final String status;

        TemporaryJobDelegationStatus(String status) {
                this.status = status;
        }

        public String getStatus() {
                return status;
        }
}
