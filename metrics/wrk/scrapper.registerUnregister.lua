wrk.method = "POST"

function request()
    -- Generate a random chat ID for every request
    chat_id = math.random(1000000000, 9999999999)
    wrk.path = "/tg-chat/" .. chat_id

    return wrk.format(nil, "/tg-chat/" .. chat_id, nil, nil)
end

function response(status, headers, body)
    -- FIX: It doesn't stop after the delete request, it keeps sending DELETE requests
    if status == 200 then
        wrk.method = "DELETE"
    else
        -- Exit script execution if any request failed
        wrk.thread:stop()
    end
end
