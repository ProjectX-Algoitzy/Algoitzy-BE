local key = KEYS[1]

local viewCount = tonumber(redis.call('GET', key) or 0)
redis.call('SET', key, viewCount + 1)