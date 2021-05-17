package xiaoqiangZzz.api.dealBox.service;

import xiaoqiangZzz.api.dealBox.entity.Goods;

import java.util.List;

public interface GoodsService {
  Goods add(Goods goods);

  Goods getById(Long id);

  List<Goods> getAll();
}
