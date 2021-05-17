package xiaoqiangZzz.api.dealBox.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xiaoqiangZzz.api.dealBox.entity.Goods;
import xiaoqiangZzz.api.dealBox.repository.GoodsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
  private final GoodsRepository goodsRepository;
  private final UserService userService;

  public GoodsServiceImpl(GoodsRepository goodsRepository,
                          UserService userService) {
    this.goodsRepository = goodsRepository;
    this.userService = userService;
  }

  @Override
  public Goods add(Goods goods) {
    goods.setCreateUser(this.userService.getCurrentUser());
    return this.goodsRepository.save(goods);
  }

  @Override
  public Goods getById(Long id) {
    return this.goodsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("找不到相关商品"));
  }

  @Override
  public List<Goods> getAll() {
    return this.goodsRepository.findAllByOrderByIdDesc();
  }
}
