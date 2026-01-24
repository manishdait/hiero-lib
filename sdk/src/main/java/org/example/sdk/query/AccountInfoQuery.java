package org.example.sdk.query;

import com.hedera.hashgraph.sdk.proto.QueryHeader;
import com.hedera.hashgraph.sdk.proto.Transaction;
import com.hedera.hashgraph.sdk.proto.CryptoGetInfoQuery;
import com.hedera.hashgraph.sdk.proto.ResponseType;
import com.hedera.hashgraph.sdk.proto.CryptoTransferTransactionBody;
import com.hedera.hashgraph.sdk.proto.TransferList;
import com.hedera.hashgraph.sdk.proto.AccountAmount;
import com.hedera.hashgraph.sdk.proto.TransactionBody;
import com.hedera.hashgraph.sdk.proto.TransactionID;
import com.hedera.hashgraph.sdk.proto.Timestamp;
import com.hedera.hashgraph.sdk.proto.Duration;
import com.hedera.hashgraph.sdk.proto.SignedTransaction;
import com.hedera.hashgraph.sdk.proto.SignatureMap;
import com.hedera.hashgraph.sdk.proto.SignaturePair;
import com.hedera.hashgraph.sdk.proto.CryptoServiceGrpc;
import com.hedera.hashgraph.sdk.proto.Response;
import com.hedera.hashgraph.sdk.proto.ResponseHeader;

import com.google.protobuf.ByteString;
import io.grpc.MethodDescriptor;
import org.example.sdk.Client;
import org.example.sdk.account.AccountId;
import org.example.sdk.account.AccountInfo;
import org.jspecify.annotations.NonNull;

import java.time.Instant;
import java.util.Objects;

public class AccountInfoQuery extends Query {
  private AccountId accountId;

  public AccountInfoQuery() {}

  public AccountId getAccountId() {
    return this.accountId;
  }

  public AccountInfoQuery withAccountId(@NonNull final AccountId accountId) {
    Objects.requireNonNull(accountId, "accountId must not be null");
    this.accountId = accountId;
    return this;
  }

  @Override
  public com.hedera.hashgraph.sdk.proto.Query toProto() {
    var query = CryptoGetInfoQuery.newBuilder()
      .setAccountID(this.accountId.toProto())
      .setHeader(this.queryHeader)
      .build();

    return com.hedera.hashgraph.sdk.proto.Query.newBuilder()
      .setCryptoGetInfo(query)
      .build();
  }

  @Override
  protected MethodDescriptor<com.hedera.hashgraph.sdk.proto.Query, Response> getMethodDescriptor() {
    return CryptoServiceGrpc.getGetAccountInfoMethod();
  }


  @Override
  protected ResponseHeader getResponseHeader(@NonNull final Response response) {
    Objects.requireNonNull(response, "response must not be null");
    return response.getCryptoGetInfo().getHeader();
  }

  public AccountInfo query(@NonNull Client client) {
    Objects.requireNonNull(client, "client must not be null");
    this.doPreQueryCheck(client);

    var infoProto = this.execute(client).getCryptoGetInfo().getAccountInfo();
    return AccountInfo.fromProto(infoProto);
  }
}
