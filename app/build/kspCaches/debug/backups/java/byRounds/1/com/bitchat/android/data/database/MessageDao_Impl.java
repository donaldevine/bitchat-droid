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
import com.bitchat.android.data.model.Message;
import com.bitchat.android.data.model.MessagePriority;
import com.bitchat.android.data.model.MessageType;
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
public final class MessageDao_Impl implements MessageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Message> __insertionAdapterOfMessage;

  private final BitChatTypeConverters __bitChatTypeConverters = new BitChatTypeConverters();

  private final EntityDeletionOrUpdateAdapter<Message> __deletionAdapterOfMessage;

  private final EntityDeletionOrUpdateAdapter<Message> __updateAdapterOfMessage;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExpiredMessages;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsDelivered;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsRead;

  private final SharedSQLiteStatement __preparedStmtOfMarkChannelMessagesAsRead;

  private final SharedSQLiteStatement __preparedStmtOfMarkDirectMessagesAsRead;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMessagesForChannel;

  public MessageDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMessage = new EntityInsertionAdapter<Message>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `messages` (`id`,`senderId`,`recipientId`,`channelId`,`content`,`timestamp`,`messageType`,`encryptedContent`,`signature`,`isDelivered`,`isRead`,`hopCount`,`maxHops`,`ttl`,`priority`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Message entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSenderId());
        if (entity.getRecipientId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getRecipientId());
        }
        if (entity.getChannelId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getChannelId());
        }
        statement.bindString(5, entity.getContent());
        statement.bindLong(6, entity.getTimestamp());
        final String _tmp = __bitChatTypeConverters.fromMessageType(entity.getMessageType());
        statement.bindString(7, _tmp);
        if (entity.getEncryptedContent() == null) {
          statement.bindNull(8);
        } else {
          statement.bindBlob(8, entity.getEncryptedContent());
        }
        if (entity.getSignature() == null) {
          statement.bindNull(9);
        } else {
          statement.bindBlob(9, entity.getSignature());
        }
        final int _tmp_1 = entity.isDelivered() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final int _tmp_2 = entity.isRead() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        statement.bindLong(12, entity.getHopCount());
        statement.bindLong(13, entity.getMaxHops());
        statement.bindLong(14, entity.getTtl());
        final String _tmp_3 = __bitChatTypeConverters.fromMessagePriority(entity.getPriority());
        statement.bindString(15, _tmp_3);
      }
    };
    this.__deletionAdapterOfMessage = new EntityDeletionOrUpdateAdapter<Message>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `messages` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Message entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfMessage = new EntityDeletionOrUpdateAdapter<Message>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `messages` SET `id` = ?,`senderId` = ?,`recipientId` = ?,`channelId` = ?,`content` = ?,`timestamp` = ?,`messageType` = ?,`encryptedContent` = ?,`signature` = ?,`isDelivered` = ?,`isRead` = ?,`hopCount` = ?,`maxHops` = ?,`ttl` = ?,`priority` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Message entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSenderId());
        if (entity.getRecipientId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getRecipientId());
        }
        if (entity.getChannelId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getChannelId());
        }
        statement.bindString(5, entity.getContent());
        statement.bindLong(6, entity.getTimestamp());
        final String _tmp = __bitChatTypeConverters.fromMessageType(entity.getMessageType());
        statement.bindString(7, _tmp);
        if (entity.getEncryptedContent() == null) {
          statement.bindNull(8);
        } else {
          statement.bindBlob(8, entity.getEncryptedContent());
        }
        if (entity.getSignature() == null) {
          statement.bindNull(9);
        } else {
          statement.bindBlob(9, entity.getSignature());
        }
        final int _tmp_1 = entity.isDelivered() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final int _tmp_2 = entity.isRead() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        statement.bindLong(12, entity.getHopCount());
        statement.bindLong(13, entity.getMaxHops());
        statement.bindLong(14, entity.getTtl());
        final String _tmp_3 = __bitChatTypeConverters.fromMessagePriority(entity.getPriority());
        statement.bindString(15, _tmp_3);
        statement.bindString(16, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteExpiredMessages = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM messages WHERE ttl < ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsDelivered = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE messages SET isDelivered = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsRead = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE messages SET isRead = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkChannelMessagesAsRead = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE messages SET isRead = 1 WHERE channelId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkDirectMessagesAsRead = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE messages SET isRead = 1 WHERE (senderId = ? OR recipientId = ?) AND channelId IS NULL";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteMessagesForChannel = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM messages WHERE channelId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertMessage(final Message message, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMessage.insert(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertMessages(final List<Message> messages,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMessage.insert(messages);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMessage(final Message message, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMessage.handle(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMessage(final Message message, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMessage.handle(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteExpiredMessages(final long currentTime,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExpiredMessages.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, currentTime);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteExpiredMessages.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsDelivered(final String messageId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsDelivered.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, messageId);
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
          __preparedStmtOfMarkAsDelivered.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsRead(final String messageId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsRead.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, messageId);
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
          __preparedStmtOfMarkAsRead.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markChannelMessagesAsRead(final String channelId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkChannelMessagesAsRead.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, channelId);
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
          __preparedStmtOfMarkChannelMessagesAsRead.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markDirectMessagesAsRead(final String peerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkDirectMessagesAsRead.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, peerId);
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
          __preparedStmtOfMarkDirectMessagesAsRead.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMessagesForChannel(final String channelId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMessagesForChannel.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, channelId);
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
          __preparedStmtOfDeleteMessagesForChannel.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Message>> getAllMessages() {
    final String _sql = "SELECT * FROM messages ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"messages"}, new Callable<List<Message>>() {
      @Override
      @NonNull
      public List<Message> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfRecipientId = CursorUtil.getColumnIndexOrThrow(_cursor, "recipientId");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfEncryptedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedContent");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfIsDelivered = CursorUtil.getColumnIndexOrThrow(_cursor, "isDelivered");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfMaxHops = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHops");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final List<Message> _result = new ArrayList<Message>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Message _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSenderId;
            _tmpSenderId = _cursor.getString(_cursorIndexOfSenderId);
            final String _tmpRecipientId;
            if (_cursor.isNull(_cursorIndexOfRecipientId)) {
              _tmpRecipientId = null;
            } else {
              _tmpRecipientId = _cursor.getString(_cursorIndexOfRecipientId);
            }
            final String _tmpChannelId;
            if (_cursor.isNull(_cursorIndexOfChannelId)) {
              _tmpChannelId = null;
            } else {
              _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final MessageType _tmpMessageType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfMessageType);
            _tmpMessageType = __bitChatTypeConverters.toMessageType(_tmp);
            final byte[] _tmpEncryptedContent;
            if (_cursor.isNull(_cursorIndexOfEncryptedContent)) {
              _tmpEncryptedContent = null;
            } else {
              _tmpEncryptedContent = _cursor.getBlob(_cursorIndexOfEncryptedContent);
            }
            final byte[] _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getBlob(_cursorIndexOfSignature);
            }
            final boolean _tmpIsDelivered;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDelivered);
            _tmpIsDelivered = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final int _tmpMaxHops;
            _tmpMaxHops = _cursor.getInt(_cursorIndexOfMaxHops);
            final long _tmpTtl;
            _tmpTtl = _cursor.getLong(_cursorIndexOfTtl);
            final MessagePriority _tmpPriority;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfPriority);
            _tmpPriority = __bitChatTypeConverters.toMessagePriority(_tmp_3);
            _item = new Message(_tmpId,_tmpSenderId,_tmpRecipientId,_tmpChannelId,_tmpContent,_tmpTimestamp,_tmpMessageType,_tmpEncryptedContent,_tmpSignature,_tmpIsDelivered,_tmpIsRead,_tmpHopCount,_tmpMaxHops,_tmpTtl,_tmpPriority);
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
  public Flow<List<Message>> getMessagesForChannel(final String channelId) {
    final String _sql = "SELECT * FROM messages WHERE channelId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, channelId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"messages"}, new Callable<List<Message>>() {
      @Override
      @NonNull
      public List<Message> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfRecipientId = CursorUtil.getColumnIndexOrThrow(_cursor, "recipientId");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfEncryptedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedContent");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfIsDelivered = CursorUtil.getColumnIndexOrThrow(_cursor, "isDelivered");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfMaxHops = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHops");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final List<Message> _result = new ArrayList<Message>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Message _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSenderId;
            _tmpSenderId = _cursor.getString(_cursorIndexOfSenderId);
            final String _tmpRecipientId;
            if (_cursor.isNull(_cursorIndexOfRecipientId)) {
              _tmpRecipientId = null;
            } else {
              _tmpRecipientId = _cursor.getString(_cursorIndexOfRecipientId);
            }
            final String _tmpChannelId;
            if (_cursor.isNull(_cursorIndexOfChannelId)) {
              _tmpChannelId = null;
            } else {
              _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final MessageType _tmpMessageType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfMessageType);
            _tmpMessageType = __bitChatTypeConverters.toMessageType(_tmp);
            final byte[] _tmpEncryptedContent;
            if (_cursor.isNull(_cursorIndexOfEncryptedContent)) {
              _tmpEncryptedContent = null;
            } else {
              _tmpEncryptedContent = _cursor.getBlob(_cursorIndexOfEncryptedContent);
            }
            final byte[] _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getBlob(_cursorIndexOfSignature);
            }
            final boolean _tmpIsDelivered;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDelivered);
            _tmpIsDelivered = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final int _tmpMaxHops;
            _tmpMaxHops = _cursor.getInt(_cursorIndexOfMaxHops);
            final long _tmpTtl;
            _tmpTtl = _cursor.getLong(_cursorIndexOfTtl);
            final MessagePriority _tmpPriority;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfPriority);
            _tmpPriority = __bitChatTypeConverters.toMessagePriority(_tmp_3);
            _item = new Message(_tmpId,_tmpSenderId,_tmpRecipientId,_tmpChannelId,_tmpContent,_tmpTimestamp,_tmpMessageType,_tmpEncryptedContent,_tmpSignature,_tmpIsDelivered,_tmpIsRead,_tmpHopCount,_tmpMaxHops,_tmpTtl,_tmpPriority);
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
  public Flow<List<Message>> getDirectMessagesWithPeer(final String peerId) {
    final String _sql = "SELECT * FROM messages WHERE (senderId = ? OR recipientId = ?) AND channelId IS NULL ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, peerId);
    _argIndex = 2;
    _statement.bindString(_argIndex, peerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"messages"}, new Callable<List<Message>>() {
      @Override
      @NonNull
      public List<Message> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfRecipientId = CursorUtil.getColumnIndexOrThrow(_cursor, "recipientId");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfEncryptedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedContent");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfIsDelivered = CursorUtil.getColumnIndexOrThrow(_cursor, "isDelivered");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfMaxHops = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHops");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final List<Message> _result = new ArrayList<Message>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Message _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSenderId;
            _tmpSenderId = _cursor.getString(_cursorIndexOfSenderId);
            final String _tmpRecipientId;
            if (_cursor.isNull(_cursorIndexOfRecipientId)) {
              _tmpRecipientId = null;
            } else {
              _tmpRecipientId = _cursor.getString(_cursorIndexOfRecipientId);
            }
            final String _tmpChannelId;
            if (_cursor.isNull(_cursorIndexOfChannelId)) {
              _tmpChannelId = null;
            } else {
              _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final MessageType _tmpMessageType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfMessageType);
            _tmpMessageType = __bitChatTypeConverters.toMessageType(_tmp);
            final byte[] _tmpEncryptedContent;
            if (_cursor.isNull(_cursorIndexOfEncryptedContent)) {
              _tmpEncryptedContent = null;
            } else {
              _tmpEncryptedContent = _cursor.getBlob(_cursorIndexOfEncryptedContent);
            }
            final byte[] _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getBlob(_cursorIndexOfSignature);
            }
            final boolean _tmpIsDelivered;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDelivered);
            _tmpIsDelivered = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final int _tmpMaxHops;
            _tmpMaxHops = _cursor.getInt(_cursorIndexOfMaxHops);
            final long _tmpTtl;
            _tmpTtl = _cursor.getLong(_cursorIndexOfTtl);
            final MessagePriority _tmpPriority;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfPriority);
            _tmpPriority = __bitChatTypeConverters.toMessagePriority(_tmp_3);
            _item = new Message(_tmpId,_tmpSenderId,_tmpRecipientId,_tmpChannelId,_tmpContent,_tmpTimestamp,_tmpMessageType,_tmpEncryptedContent,_tmpSignature,_tmpIsDelivered,_tmpIsRead,_tmpHopCount,_tmpMaxHops,_tmpTtl,_tmpPriority);
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
  public Object getMessageById(final String messageId,
      final Continuation<? super Message> $completion) {
    final String _sql = "SELECT * FROM messages WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, messageId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Message>() {
      @Override
      @Nullable
      public Message call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfRecipientId = CursorUtil.getColumnIndexOrThrow(_cursor, "recipientId");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfEncryptedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedContent");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfIsDelivered = CursorUtil.getColumnIndexOrThrow(_cursor, "isDelivered");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfMaxHops = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHops");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final Message _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSenderId;
            _tmpSenderId = _cursor.getString(_cursorIndexOfSenderId);
            final String _tmpRecipientId;
            if (_cursor.isNull(_cursorIndexOfRecipientId)) {
              _tmpRecipientId = null;
            } else {
              _tmpRecipientId = _cursor.getString(_cursorIndexOfRecipientId);
            }
            final String _tmpChannelId;
            if (_cursor.isNull(_cursorIndexOfChannelId)) {
              _tmpChannelId = null;
            } else {
              _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final MessageType _tmpMessageType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfMessageType);
            _tmpMessageType = __bitChatTypeConverters.toMessageType(_tmp);
            final byte[] _tmpEncryptedContent;
            if (_cursor.isNull(_cursorIndexOfEncryptedContent)) {
              _tmpEncryptedContent = null;
            } else {
              _tmpEncryptedContent = _cursor.getBlob(_cursorIndexOfEncryptedContent);
            }
            final byte[] _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getBlob(_cursorIndexOfSignature);
            }
            final boolean _tmpIsDelivered;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDelivered);
            _tmpIsDelivered = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final int _tmpMaxHops;
            _tmpMaxHops = _cursor.getInt(_cursorIndexOfMaxHops);
            final long _tmpTtl;
            _tmpTtl = _cursor.getLong(_cursorIndexOfTtl);
            final MessagePriority _tmpPriority;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfPriority);
            _tmpPriority = __bitChatTypeConverters.toMessagePriority(_tmp_3);
            _result = new Message(_tmpId,_tmpSenderId,_tmpRecipientId,_tmpChannelId,_tmpContent,_tmpTimestamp,_tmpMessageType,_tmpEncryptedContent,_tmpSignature,_tmpIsDelivered,_tmpIsRead,_tmpHopCount,_tmpMaxHops,_tmpTtl,_tmpPriority);
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
  public Object getPendingMessages(final long currentTime,
      final Continuation<? super List<Message>> $completion) {
    final String _sql = "SELECT * FROM messages WHERE ttl > ? AND isDelivered = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Message>>() {
      @Override
      @NonNull
      public List<Message> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfRecipientId = CursorUtil.getColumnIndexOrThrow(_cursor, "recipientId");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfEncryptedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedContent");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfIsDelivered = CursorUtil.getColumnIndexOrThrow(_cursor, "isDelivered");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfMaxHops = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHops");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final List<Message> _result = new ArrayList<Message>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Message _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSenderId;
            _tmpSenderId = _cursor.getString(_cursorIndexOfSenderId);
            final String _tmpRecipientId;
            if (_cursor.isNull(_cursorIndexOfRecipientId)) {
              _tmpRecipientId = null;
            } else {
              _tmpRecipientId = _cursor.getString(_cursorIndexOfRecipientId);
            }
            final String _tmpChannelId;
            if (_cursor.isNull(_cursorIndexOfChannelId)) {
              _tmpChannelId = null;
            } else {
              _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final MessageType _tmpMessageType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfMessageType);
            _tmpMessageType = __bitChatTypeConverters.toMessageType(_tmp);
            final byte[] _tmpEncryptedContent;
            if (_cursor.isNull(_cursorIndexOfEncryptedContent)) {
              _tmpEncryptedContent = null;
            } else {
              _tmpEncryptedContent = _cursor.getBlob(_cursorIndexOfEncryptedContent);
            }
            final byte[] _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getBlob(_cursorIndexOfSignature);
            }
            final boolean _tmpIsDelivered;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDelivered);
            _tmpIsDelivered = _tmp_1 != 0;
            final boolean _tmpIsRead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_2 != 0;
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final int _tmpMaxHops;
            _tmpMaxHops = _cursor.getInt(_cursorIndexOfMaxHops);
            final long _tmpTtl;
            _tmpTtl = _cursor.getLong(_cursorIndexOfTtl);
            final MessagePriority _tmpPriority;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfPriority);
            _tmpPriority = __bitChatTypeConverters.toMessagePriority(_tmp_3);
            _item = new Message(_tmpId,_tmpSenderId,_tmpRecipientId,_tmpChannelId,_tmpContent,_tmpTimestamp,_tmpMessageType,_tmpEncryptedContent,_tmpSignature,_tmpIsDelivered,_tmpIsRead,_tmpHopCount,_tmpMaxHops,_tmpTtl,_tmpPriority);
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
  public Object getMessagesByType(final MessageType type, final int limit,
      final Continuation<? super List<Message>> $completion) {
    final String _sql = "SELECT * FROM messages WHERE messageType = ? ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __bitChatTypeConverters.fromMessageType(type);
    _statement.bindString(_argIndex, _tmp);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Message>>() {
      @Override
      @NonNull
      public List<Message> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfRecipientId = CursorUtil.getColumnIndexOrThrow(_cursor, "recipientId");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfEncryptedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedContent");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfIsDelivered = CursorUtil.getColumnIndexOrThrow(_cursor, "isDelivered");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfMaxHops = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHops");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final List<Message> _result = new ArrayList<Message>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Message _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSenderId;
            _tmpSenderId = _cursor.getString(_cursorIndexOfSenderId);
            final String _tmpRecipientId;
            if (_cursor.isNull(_cursorIndexOfRecipientId)) {
              _tmpRecipientId = null;
            } else {
              _tmpRecipientId = _cursor.getString(_cursorIndexOfRecipientId);
            }
            final String _tmpChannelId;
            if (_cursor.isNull(_cursorIndexOfChannelId)) {
              _tmpChannelId = null;
            } else {
              _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final MessageType _tmpMessageType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfMessageType);
            _tmpMessageType = __bitChatTypeConverters.toMessageType(_tmp_1);
            final byte[] _tmpEncryptedContent;
            if (_cursor.isNull(_cursorIndexOfEncryptedContent)) {
              _tmpEncryptedContent = null;
            } else {
              _tmpEncryptedContent = _cursor.getBlob(_cursorIndexOfEncryptedContent);
            }
            final byte[] _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getBlob(_cursorIndexOfSignature);
            }
            final boolean _tmpIsDelivered;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsDelivered);
            _tmpIsDelivered = _tmp_2 != 0;
            final boolean _tmpIsRead;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp_3 != 0;
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final int _tmpMaxHops;
            _tmpMaxHops = _cursor.getInt(_cursorIndexOfMaxHops);
            final long _tmpTtl;
            _tmpTtl = _cursor.getLong(_cursorIndexOfTtl);
            final MessagePriority _tmpPriority;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfPriority);
            _tmpPriority = __bitChatTypeConverters.toMessagePriority(_tmp_4);
            _item = new Message(_tmpId,_tmpSenderId,_tmpRecipientId,_tmpChannelId,_tmpContent,_tmpTimestamp,_tmpMessageType,_tmpEncryptedContent,_tmpSignature,_tmpIsDelivered,_tmpIsRead,_tmpHopCount,_tmpMaxHops,_tmpTtl,_tmpPriority);
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
  public Flow<Integer> getUnreadCountForChannel(final String channelId) {
    final String _sql = "SELECT COUNT(*) FROM messages WHERE channelId = ? AND isRead = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, channelId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"messages"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Flow<Integer> getUnreadCountForPeer(final String peerId) {
    final String _sql = "SELECT COUNT(*) FROM messages WHERE (senderId = ? OR recipientId = ?) AND channelId IS NULL AND isRead = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, peerId);
    _argIndex = 2;
    _statement.bindString(_argIndex, peerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"messages"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
