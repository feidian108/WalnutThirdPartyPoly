package com.walnut.cloud.open.common.util.locks;

import com.github.jedis.lock.JedisLock;
import com.walnut.cloud.open.common.error.wechat.WxRuntimeException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * JedisPool 分布式锁
 * @deprecated 不建议使用jedis-lock这个过期组件，不可靠
 *
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Deprecated
public class JedisDistributedLock implements Lock {
  private final Pool<Jedis> jedisPool;
  private final JedisLock lock;

  public JedisDistributedLock(Pool<Jedis> jedisPool, String key){
    this.jedisPool = jedisPool;
    this.lock = new JedisLock(key);
  }

  @Override
  public void lock() {
    try (Jedis jedis = jedisPool.getResource()) {
      if (!lock.acquire(jedis)) {
        throw new WxRuntimeException("acquire timeouted");
      }
    } catch (InterruptedException e) {
      throw new WxRuntimeException("lock failed", e);
    }
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    try (Jedis jedis = jedisPool.getResource()) {
      if (!lock.acquire(jedis)) {
        throw new WxRuntimeException("acquire timeouted");
      }
    }
  }

  @Override
  public boolean tryLock() {
    try (Jedis jedis = jedisPool.getResource()) {
      return lock.acquire(jedis);
    } catch (InterruptedException e) {
      throw new WxRuntimeException("lock failed", e);
    }
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    try (Jedis jedis = jedisPool.getResource()) {
      return lock.acquire(jedis);
    }
  }

  @Override
  public void unlock() {
    try (Jedis jedis = jedisPool.getResource()) {
      lock.release(jedis);
    }
  }

  @Override
  public Condition newCondition() {
    throw new WxRuntimeException("unsupported method");
  }

}
