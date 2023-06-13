import { NextApiRequest, NextApiResponse } from 'next';
import handler, { ApiRequest } from './api';

export default async function apiHandler(req: NextApiRequest, res: NextApiResponse) {
    const { url, method, body } = req;

    const apiRequest: ApiRequest = {
        url: url as string,
        method: method as string,
        data: body,
    };

    await handler(apiRequest, res);
}
