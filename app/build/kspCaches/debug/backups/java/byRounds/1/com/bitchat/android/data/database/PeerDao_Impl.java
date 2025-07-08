package com.bitchat.android.data.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bitchat.android.data.model.Peer;
import com.bitchat.android.data.model.TrustLevel;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PeerDao_Impl implements PeerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Peer> __insertionAdapterOfPeer;

  private final BitChatTypeConverters __bitChatTypeConverters = new BitChatTypeConverters();

  private final EntityDeletionOrUpdateAdapter<Peer> __deletionAdapterOfPeer;

  private final EntityDeletionOrUpdateAdapter<Peer> __updateAdapterOfPeer;

  private final SharedSQLiteStatement __preparedStmtOfMarkAllPeersOffline;

  private final SharedSQLiteStatement __preparedStmtOfMarkPeerOnline;

  private final SharedSQLiteStatement __preparedStmtOfMarkPeerOffline;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePeerSignalStrength;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePeerBatteryLevel;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePeerTrustLevel;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePeerBlockedStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePeerRoute;

  private final SharedSQLiteStatement __preparedStmtOfCleanupStaleEntries;

  public PeerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPeer = new EntityInsertionAdapter<Peer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `peers` (`id`,`name`,`publicKey`,`bluetoothAddress`,`lastSeen`,`isOnline`,`rssi`,`batteryLevel`,`deviceInfo`,`trustLevel`,`isBlocked`,`routeHopCount`,`routeQuality`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Peer entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindBlob(3, entity.getPublicKey());
        statement.bindString(4, entity.getBluetoothAddress());
        statement.bindLong(5, entity.getLastSeen());
        final int _tmp = entity.isOnline() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getRssi());
        if (entity.getBatteryLevel() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getBatteryLevel());
        }
        if (entity.getDeviceInfo() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDeviceInfo());
        }
        final String _tmp_1 = __bitChatTypeConverters.fromTrustLevel(entity.getTrustLevel());
        statement.bindString(10, _tmp_1);
        final int _tmp_2 = entity.isBlocked() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        statement.bindLong(12, entity.getRouteHopCount());
        statement.bindDouble(13, entity.getRouteQuality());
      }
    };
    this.__deletionAdapterOfPeer = new EntityDeletionOrUpdateAdapter<Peer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `peers` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Peer entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfPeer = new EntityDeletionOrUpdateAdapter<Peer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `peers` SET `id` = ?,`name` = ?,`publicKey` = ?,`bluetoothAddress` = ?,`lastSeen` = ?,`isOnline` = ?,`rssi` = ?,`batteryLevel` = ?,`deviceInfo` = ?,`trustLevel` = ?,`isBlocked` = ?,`routeHopCount` = ?,`routeQuality` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Peer entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindBlob(3, entity.getPublicKey());
        statement.bindString(4, entity.getBluetoothAddress());
        statement.bindLong(5, entity.getLastSeen());
        final int _tmp = entity.isOnline() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getRssi());
        if (entity.getBatteryLevel() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getBatteryLevel());
        }
        if (entity.getDeviceInfo() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDeviceInfo());
        }
        final String _tmp_1 = __bitChatTypeConverters.fromTrustLevel(entity.getTrustLevel());
        statement.bindString(10, _tmp_1);
        final int _tmp_2 = entity.isBlocked() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        statement.bindLong(12, entity.getRouteHopCount());
        statement.bindDouble(13, entity.getRouteQuality());
        statement.bindString(14, entity.getId());
      }
    };
    this.__preparedStmtOfMarkAllPeersOffline = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE peers SET isOnline = 0";
        return _query;
      }
    };
    this.__preparedStmtOfMarkPeerOnline = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE peers SET isOnline = 1, lastSeen = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkPeerOffline = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE peers SET isOnline = 0, lastSeen = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePeerSignalStrength = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE peers SET rssi = ?, lastSeen = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePeerBatteryLevel = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE peers SET batteryLevel = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePeerTrustLevel = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE peers SET trustLevel = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePeerBlockedStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE peers SET isBlocked = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePeerRoute = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE peers SET routeHopCount = ?, routeQuality = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfCleanupStaleEntries = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM peers WHERE lastSeen < ? AND isOnline = 0";
        return _query;
      }
    };
  }

  @Override
  public Object insertPeer(final Peer peer, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPeer.insert(peer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPeers(final List<Peer> peers, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPeer.insert(peers);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePeer(final Peer peer, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPeer.handle(peer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePeer(final Peer peer, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPeer.handle(peer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markAllPeersOffline(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAllPeersOffline.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAllPeersOffline.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markPeerOnline(final String peerId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkPeerOnline.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, peerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkPeerOnline.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markPeerOffline(final String peerId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkPeerOffline.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, peerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkPeerOffline.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePeerSignalStrength(final String peerId, final int rssi, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePeerSignalStrength.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, rssi);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        _stmt.bindString(_argIndex, peerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePeerSignalStrength.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePeerBatteryLevel(final String peerId, final int batteryLevel,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePeerBatteryLevel.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, batteryLevel);
        _argIndex = 2;
        _stmt.bindString(_argIndex, peerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePeerBatteryLevel.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePeerTrustLevel(final String peerId, final TrustLevel trustLevel,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePeerTrustLevel.acquire();
        int _argIndex = 1;
        final String _tmp = __bitChatTypeConverters.fromTrustLevel(trustLevel);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, peerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePeerTrustLevel.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePeerBlockedStatus(final String peerId, final boolean isBlocked,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePeerBlockedStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isBlocked ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, peerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePeerBlockedStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePeerRoute(final String peerId, final int hopCount, final double quality,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePeerRoute.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, hopCount);
        _argIndex = 2;
        _stmt.bindDouble(_argIndex, quality);
        _argIndex = 3;
        _stmt.bindString(_argIndex, peerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePeerRoute.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object cleanupStaleEntries(final long threshold,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCleanupStaleEntries.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, threshold);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfCleanupStaleEntries.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Peer>> getAllPeers() {
    final String _sql = "SELECT * FROM peers ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"peers"}, new Callable<List<Peer>>() {
      @Override
      @NonNull
      public List<Peer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPublicKey = CursorUtil.getColumnIndexOrThrow(_cursor, "publicKey");
          final int _cursorIndexOfBluetoothAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "bluetoothAddress");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnline");
          final int _cursorIndexOfRssi = CursorUtil.getColumnIndexOrThrow(_cursor, "rssi");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfTrustLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "trustLevel");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfRouteHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "routeHopCount");
          final int _cursorIndexOfRouteQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "routeQuality");
          final List<Peer> _result = new ArrayList<Peer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Peer _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final byte[] _tmpPublicKey;
            _tmpPublicKey = _cursor.getBlob(_cursorIndexOfPublicKey);
            final String _tmpBluetoothAddress;
            _tmpBluetoothAddress = _cursor.getString(_cursorIndexOfBluetoothAddress);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final int _tmpRssi;
            _tmpRssi = _cursor.getInt(_cursorIndexOfRssi);
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final TrustLevel _tmpTrustLevel;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTrustLevel);
            _tmpTrustLevel = __bitChatTypeConverters.toTrustLevel(_tmp_1);
            final boolean _tmpIsBlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp_2 != 0;
            final int _tmpRouteHopCount;
            _tmpRouteHopCount = _cursor.getInt(_cursorIndexOfRouteHopCount);
            final double _tmpRouteQuality;
            _tmpRouteQuality = _cursor.getDouble(_cursorIndexOfRouteQuality);
            _item = new Peer(_tmpId,_tmpName,_tmpPublicKey,_tmpBluetoothAddress,_tmpLastSeen,_tmpIsOnline,_tmpRssi,_tmpBatteryLevel,_tmpDeviceInfo,_tmpTrustLevel,_tmpIsBlocked,_tmpRouteHopCount,_tmpRouteQuality);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Peer>> getOnlinePeers() {
    final String _sql = "SELECT * FROM peers WHERE isOnline = 1 ORDER BY lastSeen DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"peers"}, new Callable<List<Peer>>() {
      @Override
      @NonNull
      public List<Peer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPublicKey = CursorUtil.getColumnIndexOrThrow(_cursor, "publicKey");
          final int _cursorIndexOfBluetoothAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "bluetoothAddress");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnline");
          final int _cursorIndexOfRssi = CursorUtil.getColumnIndexOrThrow(_cursor, "rssi");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfTrustLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "trustLevel");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfRouteHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "routeHopCount");
          final int _cursorIndexOfRouteQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "routeQuality");
          final List<Peer> _result = new ArrayList<Peer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Peer _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final byte[] _tmpPublicKey;
            _tmpPublicKey = _cursor.getBlob(_cursorIndexOfPublicKey);
            final String _tmpBluetoothAddress;
            _tmpBluetoothAddress = _cursor.getString(_cursorIndexOfBluetoothAddress);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final int _tmpRssi;
            _tmpRssi = _cursor.getInt(_cursorIndexOfRssi);
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final TrustLevel _tmpTrustLevel;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTrustLevel);
            _tmpTrustLevel = __bitChatTypeConverters.toTrustLevel(_tmp_1);
            final boolean _tmpIsBlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp_2 != 0;
            final int _tmpRouteHopCount;
            _tmpRouteHopCount = _cursor.getInt(_cursorIndexOfRouteHopCount);
            final double _tmpRouteQuality;
            _tmpRouteQuality = _cursor.getDouble(_cursorIndexOfRouteQuality);
            _item = new Peer(_tmpId,_tmpName,_tmpPublicKey,_tmpBluetoothAddress,_tmpLastSeen,_tmpIsOnline,_tmpRssi,_tmpBatteryLevel,_tmpDeviceInfo,_tmpTrustLevel,_tmpIsBlocked,_tmpRouteHopCount,_tmpRouteQuality);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPeerById(final String peerId, final Continuation<? super Peer> $completion) {
    final String _sql = "SELECT * FROM peers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, peerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Peer>() {
      @Override
      @Nullable
      public Peer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPublicKey = CursorUtil.getColumnIndexOrThrow(_cursor, "publicKey");
          final int _cursorIndexOfBluetoothAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "bluetoothAddress");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnline");
          final int _cursorIndexOfRssi = CursorUtil.getColumnIndexOrThrow(_cursor, "rssi");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfTrustLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "trustLevel");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfRouteHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "routeHopCount");
          final int _cursorIndexOfRouteQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "routeQuality");
          final Peer _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final byte[] _tmpPublicKey;
            _tmpPublicKey = _cursor.getBlob(_cursorIndexOfPublicKey);
            final String _tmpBluetoothAddress;
            _tmpBluetoothAddress = _cursor.getString(_cursorIndexOfBluetoothAddress);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final int _tmpRssi;
            _tmpRssi = _cursor.getInt(_cursorIndexOfRssi);
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final TrustLevel _tmpTrustLevel;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTrustLevel);
            _tmpTrustLevel = __bitChatTypeConverters.toTrustLevel(_tmp_1);
            final boolean _tmpIsBlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp_2 != 0;
            final int _tmpRouteHopCount;
            _tmpRouteHopCount = _cursor.getInt(_cursorIndexOfRouteHopCount);
            final double _tmpRouteQuality;
            _tmpRouteQuality = _cursor.getDouble(_cursorIndexOfRouteQuality);
            _result = new Peer(_tmpId,_tmpName,_tmpPublicKey,_tmpBluetoothAddress,_tmpLastSeen,_tmpIsOnline,_tmpRssi,_tmpBatteryLevel,_tmpDeviceInfo,_tmpTrustLevel,_tmpIsBlocked,_tmpRouteHopCount,_tmpRouteQuality);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPeerByBluetoothAddress(final String address,
      final Continuation<? super Peer> $completion) {
    final String _sql = "SELECT * FROM peers WHERE bluetoothAddress = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, address);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Peer>() {
      @Override
      @Nullable
      public Peer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPublicKey = CursorUtil.getColumnIndexOrThrow(_cursor, "publicKey");
          final int _cursorIndexOfBluetoothAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "bluetoothAddress");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnline");
          final int _cursorIndexOfRssi = CursorUtil.getColumnIndexOrThrow(_cursor, "rssi");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfTrustLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "trustLevel");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfRouteHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "routeHopCount");
          final int _cursorIndexOfRouteQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "routeQuality");
          final Peer _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final byte[] _tmpPublicKey;
            _tmpPublicKey = _cursor.getBlob(_cursorIndexOfPublicKey);
            final String _tmpBluetoothAddress;
            _tmpBluetoothAddress = _cursor.getString(_cursorIndexOfBluetoothAddress);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final int _tmpRssi;
            _tmpRssi = _cursor.getInt(_cursorIndexOfRssi);
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final TrustLevel _tmpTrustLevel;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTrustLevel);
            _tmpTrustLevel = __bitChatTypeConverters.toTrustLevel(_tmp_1);
            final boolean _tmpIsBlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp_2 != 0;
            final int _tmpRouteHopCount;
            _tmpRouteHopCount = _cursor.getInt(_cursorIndexOfRouteHopCount);
            final double _tmpRouteQuality;
            _tmpRouteQuality = _cursor.getDouble(_cursorIndexOfRouteQuality);
            _result = new Peer(_tmpId,_tmpName,_tmpPublicKey,_tmpBluetoothAddress,_tmpLastSeen,_tmpIsOnline,_tmpRssi,_tmpBatteryLevel,_tmpDeviceInfo,_tmpTrustLevel,_tmpIsBlocked,_tmpRouteHopCount,_tmpRouteQuality);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTrustedPeers(final Continuation<? super List<Peer>> $completion) {
    final String _sql = "SELECT * FROM peers WHERE isBlocked = 0 AND trustLevel != 'UNTRUSTED' ORDER BY routeQuality DESC, routeHopCount ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Peer>>() {
      @Override
      @NonNull
      public List<Peer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPublicKey = CursorUtil.getColumnIndexOrThrow(_cursor, "publicKey");
          final int _cursorIndexOfBluetoothAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "bluetoothAddress");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnline");
          final int _cursorIndexOfRssi = CursorUtil.getColumnIndexOrThrow(_cursor, "rssi");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfTrustLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "trustLevel");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfRouteHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "routeHopCount");
          final int _cursorIndexOfRouteQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "routeQuality");
          final List<Peer> _result = new ArrayList<Peer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Peer _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final byte[] _tmpPublicKey;
            _tmpPublicKey = _cursor.getBlob(_cursorIndexOfPublicKey);
            final String _tmpBluetoothAddress;
            _tmpBluetoothAddress = _cursor.getString(_cursorIndexOfBluetoothAddress);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final int _tmpRssi;
            _tmpRssi = _cursor.getInt(_cursorIndexOfRssi);
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final TrustLevel _tmpTrustLevel;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTrustLevel);
            _tmpTrustLevel = __bitChatTypeConverters.toTrustLevel(_tmp_1);
            final boolean _tmpIsBlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp_2 != 0;
            final int _tmpRouteHopCount;
            _tmpRouteHopCount = _cursor.getInt(_cursorIndexOfRouteHopCount);
            final double _tmpRouteQuality;
            _tmpRouteQuality = _cursor.getDouble(_cursorIndexOfRouteQuality);
            _item = new Peer(_tmpId,_tmpName,_tmpPublicKey,_tmpBluetoothAddress,_tmpLastSeen,_tmpIsOnline,_tmpRssi,_tmpBatteryLevel,_tmpDeviceInfo,_tmpTrustLevel,_tmpIsBlocked,_tmpRouteHopCount,_tmpRouteQuality);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getReachablePeers(final int maxHops,
      final Continuation<? super List<Peer>> $completion) {
    final String _sql = "SELECT * FROM peers WHERE isBlocked = 0 AND routeHopCount < ? ORDER BY routeQuality DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, maxHops);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Peer>>() {
      @Override
      @NonNull
      public List<Peer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPublicKey = CursorUtil.getColumnIndexOrThrow(_cursor, "publicKey");
          final int _cursorIndexOfBluetoothAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "bluetoothAddress");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnline");
          final int _cursorIndexOfRssi = CursorUtil.getColumnIndexOrThrow(_cursor, "rssi");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfTrustLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "trustLevel");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfRouteHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "routeHopCount");
          final int _cursorIndexOfRouteQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "routeQuality");
          final List<Peer> _result = new ArrayList<Peer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Peer _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final byte[] _tmpPublicKey;
            _tmpPublicKey = _cursor.getBlob(_cursorIndexOfPublicKey);
            final String _tmpBluetoothAddress;
            _tmpBluetoothAddress = _cursor.getString(_cursorIndexOfBluetoothAddress);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsOnline;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp != 0;
            final int _tmpRssi;
            _tmpRssi = _cursor.getInt(_cursorIndexOfRssi);
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final TrustLevel _tmpTrustLevel;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTrustLevel);
            _tmpTrustLevel = __bitChatTypeConverters.toTrustLevel(_tmp_1);
            final boolean _tmpIsBlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp_2 != 0;
            final int _tmpRouteHopCount;
            _tmpRouteHopCount = _cursor.getInt(_cursorIndexOfRouteHopCount);
            final double _tmpRouteQuality;
            _tmpRouteQuality = _cursor.getDouble(_cursorIndexOfRouteQuality);
            _item = new Peer(_tmpId,_tmpName,_tmpPublicKey,_tmpBluetoothAddress,_tmpLastSeen,_tmpIsOnline,_tmpRssi,_tmpBatteryLevel,_tmpDeviceInfo,_tmpTrustLevel,_tmpIsBlocked,_tmpRouteHopCount,_tmpRouteQuality);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
