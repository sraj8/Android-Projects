// FedMoney.aidl
package com.uic.cs478.sylvesterraj.fedmoneycommon;

// Declare any non-default types here with import statements

interface FedMoney {
    List monthlyAvgCash(int aYear);
    List dailyCash(int aYear, int aMonth, int aDay, int aNumber);
}
