package org.example.sdk.internal;

import org.example.sdk.network.NetworkType;

import java.util.Map;

public class Config {
  public static final int MAX_ATTEMPTS = 10;

  public static final Map<NetworkType, String> MIRROR_NODE_ADDRESS = Map.of(
    NetworkType.MAINNET, "mainnet.mirrornode.hedera.com:443",
    NetworkType.PREVIEWNET, "previewnet.mirrornode.hedera.com:443",
    NetworkType.TESTNET, "testnet.mirrornode.hedera.com:443",
    NetworkType.SOLO, "localhost:5600"
  );
}
