package dev.nk7.bot.notifier.persistence.rocksdb;

import org.rocksdb.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class Rocks<T extends Enum<T>> {
  static {
    RocksDB.loadLibrary();
  }

  private final RocksDB rocksDB;
  private final EnumMap<T, ColumnFamilyHandle> handles;

  public Rocks(RocksProperties props, Class<T> mapsType) {
    this.handles = new EnumMap<>(mapsType);
    final DBOptions dbOptions = new DBOptions();
    dbOptions.setCreateIfMissing(true);
    dbOptions.setCreateMissingColumnFamilies(true);


    final List<ColumnFamilyDescriptor> descriptors = new ArrayList<>();
    descriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY));

    final T[] enumConstants = mapsType.getEnumConstants();
    Arrays.stream(enumConstants)
      .map(c -> c.name().getBytes())
      .map(ColumnFamilyDescriptor::new)
      .forEach(descriptors::add);

    final ArrayList<ColumnFamilyHandle> handlesTmp = new ArrayList<>(descriptors.size());
    try {
      this.rocksDB = RocksDB.open(dbOptions,
        props.path(),
        descriptors,
        handlesTmp
      );
    } catch (RocksDBException e) {
      throw new PersistenceException("Ошибка создания Rocks.", e);
    }

    for (int i = 1; i < handlesTmp.size(); i++) {
      final ColumnFamilyHandle handle = handlesTmp.get(i);
      handles.put(enumConstants[i - 1], handle);
    }
  }

  public void put(T map, byte[] key, byte[] value) {
    final ColumnFamilyHandle handle = handles.get(map);
    try {
      rocksDB.put(handle, key, value);
    } catch (RocksDBException e) {
      throw new PersistenceException("Ошибка сохранения в Column Family " + map, e);
    }
  }

  public byte[] get(T map, byte[] key) {
    final ColumnFamilyHandle handle = handles.get(map);
    try {
      return rocksDB.get(handle, key);
    } catch (RocksDBException e) {
      throw new PersistenceException("Ошибка получения из Column Family " + map, e);
    }
  }
}
