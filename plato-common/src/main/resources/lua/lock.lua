if redis.call('EXISTS', KEYS[1]) == 0
then
    -- 如果key不存在 则加锁
    redis.call('set', KEYS[1], ARGV[1])
    redis.call('expire', KEYS[1], ARGV[2])
    return 1
elseif redis.call('get', KEYS[1]) == ARGV[1]
then
    -- 如果key存在并且value等于account_no 则说明是同一个业务线程 可重入
    return 2
else
    return 0
end