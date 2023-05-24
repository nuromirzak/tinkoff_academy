wrk.method = "POST"
wrk.headers["Content-Type"] = "application/json"

function request()
    local id = math.random(1, 1000)
    local url = "https://example.com/page/" .. id
    local description = "Doing benchmarking with wrk. You've got a link: " .. url
    -- Change this to your own chat id
    local tgChatIds = { 362037700 }

    local payload = string.format(
        '{"id": %d, "url": "%s", "description": "%s", "tgChatIds": [%d]}',
        id, url, description, tgChatIds[1]
    )

    return wrk.format(nil, "/updates", nil, payload)
end
