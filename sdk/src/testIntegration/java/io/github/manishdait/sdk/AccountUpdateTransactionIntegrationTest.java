package io.github.manishdait.sdk;

import io.github.manishdait.sdk.account.AccountCreateTransaction;
import io.github.manishdait.sdk.account.AccountUpdateTransaction;
import io.github.manishdait.sdk.key.PrivateKey;
import io.github.manishdait.sdk.query.AccountInfoQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountUpdateTransactionIntegrationTest {
  Client client;

  @BeforeEach
  void setup() {
    client = Client.fromEnv();
  }

  @Test
  void shouldUpdateAccount() {
    var privateKey = PrivateKey.generate();

    var createAccountReceipt =
      new AccountCreateTransaction()
        .withKey(privateKey)
        .withAccountMemo("sdk:create-account:test")
        .withInitialBalance(Hbar.of(1))
        .pack(client)
        .send()
        .queryReceipt();


    var info = new AccountInfoQuery().withAccountId(createAccountReceipt.accountId()).query(client);

    assertThat(info).isNotNull();
    assertThat(info.memo()).isEqualTo("sdk:create-account:test");

    var tx = new AccountUpdateTransaction()
      .withAccountId(createAccountReceipt.accountId())
      .withAccountMemo("sdk:update-account:test")
      .pack(client)
      .signWith(privateKey);

    var response = tx.send();
    var receipt = response.queryReceipt();

    assertThat(receipt).isNotNull();
    assertThat(receipt.accountId()).isNotNull();

    var info2 = new AccountInfoQuery().withAccountId(receipt.accountId()).query(client);

    assertThat(info2).isNotNull();
    assertThat(info2.memo()).isEqualTo("sdk:update-account:test");
  }
}
